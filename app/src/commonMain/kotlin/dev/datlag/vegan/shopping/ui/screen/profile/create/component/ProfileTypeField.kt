package dev.datlag.vegan.shopping.ui.screen.profile.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.common.onClick
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.ui.screen.profile.create.CreateProfileComponent
import dev.datlag.vegan.shopping.ui.theme.MaterialSymbols
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTypeField(component: CreateProfileComponent) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Restaurant,
            contentDescription = null
        )
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val selectedOption by component.foodType.collectAsStateWithLifecycle()

            // replace with ExposedDropdownMenu
            // with OutlinedTextField, like before
            // place box above text field with onClick listener to ensure drop-down opens
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(
                    text = "Food Type",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = selectedOption.displayIcon(),
                        contentDescription = selectedOption.displayText(),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = selectedOption.displayText(),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Slider(
                value = selectedOption.sliderValue(),
                onValueChange = {
                    component.selectFoodType(foodTypeBySliderValue(it))
                },
                steps = 1,
                valueRange = 0F..2F
            )
        }
    }
}

@Composable
private fun FoodType.displayText(): String {
    return when (this) {
        is FoodType.VEGAN -> stringResource(SharedRes.strings.vegan)
        is FoodType.VEGETARIAN -> stringResource(SharedRes.strings.vegetarian)
        is FoodType.OMNIVORE -> stringResource(SharedRes.strings.omnivore)
    }
}

@Composable
private fun FoodType.displayIcon(): ImageVector {
    return when (this) {
        is FoodType.VEGAN -> MaterialSymbols.rememberNestEcoLeaf()
        is FoodType.VEGETARIAN -> MaterialSymbols.rememberTempPreferencesEco()
        is FoodType.OMNIVORE -> Icons.Default.SetMeal
    }
}

private fun FoodType.sliderValue(): Float {
    return when (this) {
        is FoodType.VEGAN -> 2F
        is FoodType.VEGETARIAN -> 1F
        is FoodType.OMNIVORE -> 0F
    }
}

private fun foodTypeBySliderValue(value: Float): FoodType {
    return when (value) {
        2F -> FoodType.VEGAN
        1F-> FoodType.VEGETARIAN
        else -> FoodType.OMNIVORE
    }
}
