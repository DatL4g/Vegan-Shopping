package dev.datlag.vegan.shopping.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.model.openfoodfacts.Ingredient
import dev.datlag.vegan.shopping.ui.theme.MaterialSymbols
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ingredientTypeDisplayText(type: Ingredient.Type): String {
    return when (type) {
        is Ingredient.Type.VEGAN -> {
            if (type.maybe) {
                stringResource(SharedRes.strings.vegetarian)
            } else {
                stringResource(SharedRes.strings.vegan)
            }
        }
        is Ingredient.Type.VEGETARIAN -> stringResource(SharedRes.strings.vegetarian)
        is Ingredient.Type.OMNIVORE -> stringResource(SharedRes.strings.omnivore)
        is Ingredient.Type.UNKNOWN -> stringResource(SharedRes.strings.unknown)
    }
}

@Composable
fun ingredientTypeDisplayIcon(type: Ingredient.Type): ImageVector {
    return when (type) {
        is Ingredient.Type.VEGAN -> {
            if (type.maybe) {
                MaterialSymbols.rememberTempPreferencesEco()
            } else {
                MaterialSymbols.rememberNestEcoLeaf()
            }
        }
        is Ingredient.Type.VEGETARIAN -> {
            MaterialSymbols.rememberTempPreferencesEco()
        }
        is Ingredient.Type.OMNIVORE -> {
            MaterialSymbols.rememberCrueltyFree()
        }
        is Ingredient.Type.UNKNOWN -> {
            MaterialSymbols.rememberQuestionMark()
        }
    }
}
