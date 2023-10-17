package dev.datlag.vegan.shopping.ui.screen.scan.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.safeContentPadding
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.ui.theme.MaterialSymbols
import dev.datlag.vegan.shopping.ui.screen.scan.ScanComponent
import io.github.aakira.napier.Napier
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource

@Composable
fun ProductSheet(component: ScanComponent) {
    val productState by component.productState.collectAsStateWithLifecycle(OFFRequest.WAITING)

    when (val currentState = productState) {
        is OFFRequest.Success -> {
            ProductSheet(currentState.product.product)
        }
        is OFFRequest.Error -> {

        }
        else -> { }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductSheet(product: Product) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var show by remember { mutableStateOf(true) }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                show = false
            },
            sheetState = modalBottomSheetState,
            scrimColor = Color.Transparent
        ) {
            Column(
                modifier = Modifier.safeContentPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ProductHead(product)
                Divider()
                VeganStatus(product)
            }
        }
    }
}

@Composable
private fun ProductHead(product: Product) {
    val productName = product.productNameAndLanguage.first

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.size(56.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            )
        ) {
            when (val resource = asyncPainterResource(product.imageUrl ?: String())) {
                is Resource.Success -> {
                    Image(
                        painter = resource.value,
                        contentDescription = productName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }
                else -> { }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = productName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2
            )
            Text(
                text = product.brands.joinToString(),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun VeganStatus(product: Product) {
    if (product.isVegan) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = MaterialSymbols.rememberEco(),
                contentDescription = "Vegan",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = "Vegan")
        }
    } else if (product.isVegetarian) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = MaterialSymbols.rememberTempPreferencesEco(),
                contentDescription = "Vegetarian",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = "Vegetarian")
        }
    } else if (product.isPossiblyVegan) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = MaterialSymbols.rememberEco(),
                contentDescription = "Vegan",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = "Vegan")
        }
    } else if (product.isPossiblyVegetarian) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = MaterialSymbols.rememberTempPreferencesEco(),
                contentDescription = "Maybe Vegetarian",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = "Maybe Vegetarian")
        }
    } else {
        Napier.e(product.ingredients.first { !it.isPossiblyVegetarian() }.toString())
        Text(text = "Omnivore ${product.ingredients.size}")
    }
}
