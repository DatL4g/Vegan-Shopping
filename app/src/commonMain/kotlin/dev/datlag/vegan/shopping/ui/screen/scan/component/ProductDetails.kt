package dev.datlag.vegan.shopping.ui.screen.scan.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.common.foodTypeDisplayIcon
import dev.datlag.vegan.shopping.common.foodTypeDisplayText
import dev.datlag.vegan.shopping.model.FoodType
import dev.datlag.vegan.shopping.model.openfoodfacts.Product

@Composable
fun ProductDetails(product: Product) {
    val type = remember(product) { product.foodType }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (type !is FoodType.OMNIVORE) {
            Icon(
                imageVector = foodTypeDisplayIcon(type),
                contentDescription = foodTypeDisplayText(type),
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = foodTypeDisplayText(type),
                fontWeight = FontWeight.SemiBold
            )
        } else {
            when {
                product.isPossiblyVegan -> {
                    Icon(
                        imageVector = foodTypeDisplayIcon(FoodType.VEGAN),
                        contentDescription = foodTypeDisplayText(FoodType.VEGAN),
                        modifier = Modifier.size(36.dp),
                        tint = Color.Yellow
                    )
                    Text(
                        text = "Possibly" + foodTypeDisplayText(FoodType.VEGAN),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                product.isPossiblyVegetarian -> {
                    Icon(
                        imageVector = foodTypeDisplayIcon(FoodType.VEGETARIAN),
                        contentDescription = foodTypeDisplayText(FoodType.VEGETARIAN),
                        modifier = Modifier.size(36.dp),
                        tint = Color.Yellow
                    )
                    Text(
                        text = "Possibly" + foodTypeDisplayText(FoodType.VEGETARIAN),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                else -> {
                    Icon(
                        imageVector = foodTypeDisplayIcon(type),
                        contentDescription = foodTypeDisplayText(type),
                        modifier = Modifier.size(36.dp)
                    )
                    Text(
                        text = foodTypeDisplayText(type),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}