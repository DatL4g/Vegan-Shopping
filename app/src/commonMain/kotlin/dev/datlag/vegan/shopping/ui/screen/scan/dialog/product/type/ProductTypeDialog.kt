@file:Suppress("MISSING_DEPENDENCY_CLASS", "MISSING_DEPENDENCY_SUPERCLASS")

package dev.datlag.vegan.shopping.ui.screen.scan.dialog.product.type

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.safeContentPadding
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.ingredientTypeDisplayIcon
import dev.datlag.vegan.shopping.common.ingredientTypeDisplayText
import dev.datlag.vegan.shopping.model.openfoodfacts.Ingredient
import dev.datlag.vegan.shopping.ui.LocalDarkMode
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun ProductTypeDialog(component: ProductTypeComponent) {
    AlertDialog(
        modifier = Modifier.safeDrawingPadding().safeContentPadding(),
        onDismissRequest = {
            component.dismiss()
        },
        icon = {
            Icon(
                imageVector = ingredientTypeDisplayIcon(component.product.type),
                contentDescription = ingredientTypeDisplayText(component.product.type)
            )
        },
        title = {
            Text(
                text = ingredientTypeDisplayText(component.product.type),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                component.product.ingredients.groupBy { it.type.grouping }
                    .forEach { (type, ingredients) ->
                        Column {
                            Text(
                                text = ingredientTypeDisplayText(type.toDefaultType()),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            IngredientList(ingredients)
                        }
                    }
            }
        },
        confirmButton = {
            val buttonBackground = contentColor(component.product.type) ?: LocalContentColor.current
            Button(
                onClick = {
                    component.dismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBackground,
                    contentColor = onContentColor(component.product.type) ?: contentColorFor(buttonBackground)
                )
            ) {
                Text(text = stringResource(SharedRes.strings.close))
            }
        },
        containerColor = containerColor(component.product.type)
            ?: AlertDialogDefaults.containerColor,
        iconContentColor = contentColor(component.product.type)
            ?: AlertDialogDefaults.iconContentColor,
        titleContentColor = contentColor(component.product.type)
            ?: AlertDialogDefaults.titleContentColor,
        textContentColor = contentColor(component.product.type)
            ?: AlertDialogDefaults.textContentColor
    )
}

@Composable
private fun IngredientList(list: Collection<Ingredient>, step: Int = 0) {
    list.forEach {
        Text(
            modifier = Modifier.padding(start = (8.dp * step)),
            text = stringResource(SharedRes.strings.bullet_point, it.text ?: it.id)
        )
        IngredientList(it.ingredients, step + 1)
    }
}

@Composable
private fun containerColor(productType: Ingredient.Type, isDark: Boolean = LocalDarkMode.current): Color? {
    return if (productType.isOmnivore) {
        omnivoreContainerColor(isDark)
    } else if (productType.isUnknown) {
        unknownContainerColor()
    } else {
        null
    }
}

@Composable
private fun contentColor(productType: Ingredient.Type, isDark: Boolean = LocalDarkMode.current): Color? {
    return if (productType.isOmnivore) {
        omnivoreContentColor(isDark)
    } else if (productType.isUnknown) {
        unknownContentColor()
    } else {
        null
    }
}

@Composable
private fun onContentColor(productType: Ingredient.Type, isDark: Boolean = LocalDarkMode.current): Color? {
    return if (productType.isOmnivore) {
        omnivoreOnContentColor(isDark)
    } else if (productType.isUnknown) {
        unknownOnContentColor()
    } else {
        null
    }
}

@Composable
private fun omnivoreContainerColor(isDark: Boolean = LocalDarkMode.current): Color {
    return if (isDark) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
}

@Composable
private fun omnivoreContentColor(isDark: Boolean = LocalDarkMode.current): Color {
    return if (isDark) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
}

@Composable
private fun omnivoreOnContentColor(isDark: Boolean = LocalDarkMode.current): Color {
    return if (isDark) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
}

@Composable
private fun unknownContainerColor(): Color {
    return MaterialTheme.colorScheme.background
}

@Composable
private fun unknownContentColor(): Color {
    return MaterialTheme.colorScheme.onBackground
}

@Composable
private fun unknownOnContentColor(): Color {
    return MaterialTheme.colorScheme.background
}