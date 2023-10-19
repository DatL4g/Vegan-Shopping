package dev.datlag.vegan.shopping.ui.screen.scan.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.ui.screen.scan.ScanComponent

@Composable
fun ProductInfo(component: ScanComponent, modifier: Modifier = Modifier.fillMaxSize()) {
    val productState by component.productState.collectAsStateWithLifecycle(OFFRequest.WAITING)

    val product = remember(productState) {
        when (val currentState = productState) {
            is OFFRequest.WAITING -> null
            is OFFRequest.Loading -> currentState.previousProduct
            is OFFRequest.Success -> currentState.product.product
            is OFFRequest.Error -> currentState.previousProduct
        }
    }
    var visibility by remember { mutableStateOf(false) }
    var bookmark by remember(product) { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (product != null) {
            ProductOverlay(
                product = product,
                isLoading = productState is OFFRequest.Loading,
                isError = productState is OFFRequest.Error,
                isBookmarked = bookmark,
                onClose = {
                    visibility = false
                    component.closeProductInfo(300)
                },
                onBookmark = {
                    bookmark = true
                },
                modifier = modifier
            ) {
                ProductDetails(product)
            }
        }
    }

    LaunchedEffect(product) {
        visibility = true
    }
}
