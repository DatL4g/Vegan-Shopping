package dev.datlag.vegan.shopping.model.openfoodfacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Product(
    @SerialName("abbreviated_product_name") val abbreviatedProductName: String? = null,
    @SerialName("code_tags") val codeTags: List<String> = emptyList(),
    @SerialName("generic_name") val genericName: String? = null,
    @SerialName("id") private val id: String? = null,
    @SerialName("lc") private val lc: String? = null,
    @SerialName("lang") val lang: String? = null,
    @SerialName("nova_group") val novaGroup: Int? = null,
    @SerialName("obsolete") val obsolete: String? = null,
    @SerialName("obsolete_since_date") val obsoleteSinceDate: String? = null,
    @SerialName("product_name") val productName: String? = null,
    @SerialName("product_name_en") val productNameEn: String? = null,
    @SerialName("additives_n") val additives: Int? = null,
    @SerialName("ecoscore_score") val ecoscoreScore: Int? = null,
    @SerialName("brands") val brandsCommaSeparated: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList()
) {
    val languageCode: String?
        get() = lang?.ifBlank { null } ?: lc?.ifBlank { null }

    val productNameAndLanguage: Pair<String, String?>
        get() {
            return if (!productName.isNullOrBlank()) {
                productName to languageCode
            } else if (!productNameEn.isNullOrBlank()) {
                productNameEn to "en"
            } else {
                (abbreviatedProductName?.ifBlank { null } ?: genericName ?: String()) to languageCode
            }
        }

    @Transient
    val brands: List<String> = run {
        if (brandsCommaSeparated.isNullOrBlank()) {
            emptyList()
        } else {
            brandsCommaSeparated.split(',').filterNot { it.isBlank() }
        }
    }

    val isVegan = ingredients.isNotEmpty() && ingredients.all { it.isVegan() }
    val isVegetarian = ingredients.isNotEmpty() && ingredients.all { it.isVegetarian() }

    val isPossiblyVegan = ingredients.isNotEmpty() && ingredients.all { it.isPossiblyVegan() }
    val isPossiblyVegetarian = ingredients.isNotEmpty() && ingredients.all { it.isPossiblyVegetarian() }
}
