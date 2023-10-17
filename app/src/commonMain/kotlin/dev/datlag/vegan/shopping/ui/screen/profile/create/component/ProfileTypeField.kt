package dev.datlag.vegan.shopping.ui.screen.profile.create.component

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
            var expanded by remember(selectedOption) { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedOption?.displayText() ?: String(),
                    onValueChange = {
                        expanded = true
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    label = {
                        Text(text = stringResource(SharedRes.strings.food_preference))
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = stringResource(SharedRes.strings.expand),
                            modifier = Modifier.rotate(if (expanded) 180f else 0f).clip(CircleShape).onClick {
                                expanded = !expanded
                            }
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    readOnly = true
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            component.selectFoodType(FoodType.VEGAN)
                        },
                        text = {
                            Text(text = FoodType.VEGAN.displayText())
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = FoodType.VEGAN.displayIcon(),
                                contentDescription = FoodType.VEGAN.displayText(),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            component.selectFoodType(FoodType.VEGETARIAN)
                        },
                        text = {
                            Text(text = FoodType.VEGETARIAN.displayText())
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = FoodType.VEGETARIAN.displayIcon(),
                                contentDescription = FoodType.VEGETARIAN.displayText(),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            component.selectFoodType(FoodType.OMNIVORE)
                        },
                        text = {
                            Text(text = FoodType.OMNIVORE.displayText())
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = FoodType.OMNIVORE.displayIcon(),
                                contentDescription = FoodType.OMNIVORE.displayText(),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
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