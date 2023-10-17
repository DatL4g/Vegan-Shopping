package dev.datlag.vegan.shopping.model.openfoodfacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("vegan") val veganString: String? = null,
    @SerialName("vegetarian") val vegetarianString: String? = null,
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList()
) {
    fun isVegan(): Boolean {
        val listVegan = ingredients.isNotEmpty() && ingredients.all {
            it.isVegan()
        }
        val stringVegan = !veganString.isNullOrBlank()
                && (veganString.trim().toBoolean() || veganString.trim().equals("yes", true))

        return listVegan || stringVegan
    }

    fun isVegetarian(): Boolean {
        val listVegan = ingredients.isNotEmpty() && ingredients.all {
            it.isVegetarian()
        }
        val stringVegan = !vegetarianString.isNullOrBlank()
                && (vegetarianString.trim().toBoolean() || vegetarianString.trim().equals("yes", true))

        return listVegan || stringVegan
    }

    fun isPossiblyVegan(): Boolean {
        val listVegan = ingredients.isNotEmpty() && ingredients.all {
            it.isPossiblyVegan()
        }
        val stringVegan = !veganString.isNullOrBlank()
                && (veganString.trim().toBoolean()
                    || veganString.trim().equals("yes", true)
                    || veganString.trim().equals("maybe", true))

        return listVegan || stringVegan
    }

    fun isPossiblyVegetarian(): Boolean {
        val listVegan = ingredients.isNotEmpty() && ingredients.all {
            it.isPossiblyVegetarian()
        }
        val stringVegan = !vegetarianString.isNullOrBlank()
                && (vegetarianString.trim().toBoolean()
                || vegetarianString.trim().equals("yes", true)
                || vegetarianString.trim().equals("maybe", true))

        return listVegan || stringVegan
    }
}