package dev.datlag.vegan.shopping.other

actual data object LanguageHelper {
    actual val systemLanguageTag: String
        get() = ""

    actual suspend fun translate(
        text: String,
        fromLanguage: String?,
        toLanguage: String
    ): String? {
        return null
    }

    actual suspend fun identifyLanguage(text: String): String? {
        return null
    }
}