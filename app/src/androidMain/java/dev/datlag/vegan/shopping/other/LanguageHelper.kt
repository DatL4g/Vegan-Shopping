package dev.datlag.vegan.shopping.other

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual data object LanguageHelper {

    actual val systemLanguageTag: String
        get() = Locale.getDefault().toLanguageTag()

    actual suspend fun translate(text: String, fromLanguage: String?, toLanguage: String): String? {
        val sourceLanguage = fromLanguage?.let {
            TranslateLanguage.fromLanguageTag(it)
        } ?: identifyLanguage(text)?.let {
            TranslateLanguage.fromLanguageTag(it)
        }

        if (sourceLanguage.equals(toLanguage, true)) {
            return text
        }

        return if (sourceLanguage != null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(TranslateLanguage.fromLanguageTag(toLanguage) ?: toLanguage)
                .build()
            val translator = Translation.getClient(options)
            translator.use {
                if (it.downloadModel()) {
                    it.suspendTranslate(text)
                } else {
                    null
                }
            }
        } else {
            null
        }
    }

    actual suspend fun identifyLanguage(text: String): String? = suspendCancellableCoroutine { continuation ->
        val identifier = LanguageIdentification.getClient()
        identifier.identifyLanguage(text).addOnSuccessListener { languageCode ->
            if (languageCode.equals("und", true)) {
                continuation.resume(null)
            } else {
                continuation.resume(languageCode)
            }
        }.addOnFailureListener {
            continuation.resume(null)
        }.addOnCanceledListener {
            continuation.cancel(null)
        }
    }

    private suspend fun Translator.downloadModel() = suspendCoroutine { continuation ->
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        this.downloadModelIfNeeded(conditions).addOnSuccessListener {
            continuation.resume(true)
        }.addOnFailureListener {
            continuation.resume(false)
        }.addOnCanceledListener {
            continuation.resume(false)
        }
    }

    private suspend fun Translator.suspendTranslate(text: String) = suspendCoroutine { continuation ->
        this.translate(text).addOnSuccessListener {
            continuation.resume(it)
        }.addOnFailureListener {
            continuation.resume(null)
        }.addOnCanceledListener {
            continuation.resume(null)
        }
    }
}