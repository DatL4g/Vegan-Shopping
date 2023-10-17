package dev.datlag.vegan.shopping.other

expect object LanguageHelper {

    val systemLanguageTag: String

    suspend fun translate(
        text: String,
        fromLanguage: String? = null,
        toLanguage: String
    ): String?

    suspend fun identifyLanguage(text: String): String?
}