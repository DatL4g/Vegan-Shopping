package dev.datlag.vegan.shopping.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.ui.theme.MaterialSymbols
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun foodTypeDisplayText(type: FoodType): String {
    return when (type) {
        is FoodType.VEGAN -> stringResource(SharedRes.strings.vegan)
        is FoodType.VEGETARIAN -> stringResource(SharedRes.strings.vegetarian)
        is FoodType.OMNIVORE -> stringResource(SharedRes.strings.omnivore)
    }
}

@Composable
fun foodTypeDisplayIcon(type: FoodType): ImageVector {
    return when (type) {
        is FoodType.VEGAN -> MaterialSymbols.rememberNestEcoLeaf()
        is FoodType.VEGETARIAN -> MaterialSymbols.rememberTempPreferencesEco()
        is FoodType.OMNIVORE -> Icons.Default.SetMeal
    }
}