package dev.datlag.vegan.shopping.ui.screen.profile.create.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.common.foodTypeDisplayIcon
import dev.datlag.vegan.shopping.common.foodTypeDisplayText
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.ui.screen.profile.create.CreateProfileComponent

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
                        imageVector = foodTypeDisplayIcon(selectedOption),
                        contentDescription = foodTypeDisplayText(selectedOption),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = foodTypeDisplayText(selectedOption),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Slider(
                value = sliderValue(selectedOption),
                onValueChange = {
                    component.selectFoodType(foodTypeBySliderValue(it))
                },
                steps = 1,
                valueRange = 0F..2F
            )
        }
    }
}

private fun sliderValue(type: FoodType): Float {
    return when (type) {
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
