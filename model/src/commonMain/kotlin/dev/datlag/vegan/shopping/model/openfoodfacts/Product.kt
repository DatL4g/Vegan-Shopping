package dev.datlag.vegan.shopping.model.openfoodfacts

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.datlag.vegan.shopping.model.common.contains
import dev.datlag.vegan.shopping.model.common.listFrom
import dev.datlag.vegan.shopping.model.common.setFrom
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Parcelize
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
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList(),
    @SerialName("_keywords") val keywords: List<String> = emptyList() // ToDo("add labels for vegan checking")
) : Parcelable {
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

    @Transient
    private val hasVeganKeywords = run {
        val veganLabel = keywords.contains("vegan", true)
        val veganEULabel = keywords.contains("european-vegetarian-union-vegan", true)
        return@run veganLabel || veganEULabel
    }

    @Transient
    private val hasVegetarianKeywords = run {
        val vegetarianLabel = keywords.contains("vegetarian", true)
        val vegetarianEULabel1 = keywords.contains("european-vegetarian-union", true)
        val vegetarianEULabel2 = keywords.contains("european-vegetarian-union-vegetarian", true)
        val vegetarianUnionLabel1 = keywords.contains("vegetarian-union", true)
        val vegetarianUnionLabel2 = keywords.contains("vegetarier-union", true)
        return@run vegetarianLabel
                || vegetarianEULabel1
                || vegetarianEULabel2
                || vegetarianUnionLabel1
                || vegetarianUnionLabel2
    }

    @Transient
    val type: Ingredient.Type = run {
        if (hasVeganKeywords) {
            Ingredient.Type.VEGAN(maybe = false, Ingredient.Type.VEGETARIAN(maybe = false))
        } else if (hasVegetarianKeywords) {
            Ingredient.Type.VEGETARIAN(maybe = false)
        } else {
            if (ingredients.isEmpty()) {
                Ingredient.Type.UNKNOWN
            } else {
                Ingredient.Type.mapFrom(null, null, ingredients.map { it.type })
            }
        }
    }

    @Transient
    val allIngredients: Set<Ingredient> = run {
        setFrom(ingredients, ingredients.flatMap { it.allIngredients })
    }
}
