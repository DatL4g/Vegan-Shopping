package dev.datlag.vegan.shopping.ui.screen.scan.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.common.onClick
import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.ui.screen.scan.ScanComponent
import dev.datlag.vegan.shopping.ui.theme.CutOutShape
import dev.icerock.moko.resources.compose.stringResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProductInfo(component: ScanComponent) {
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
                }
            ) {

            }
        }
    }

    LaunchedEffect(product) {
        visibility = true
    }
}

@Composable
private fun ProductOverlay(
    product: Product,
    isLoading: Boolean,
    isError: Boolean,
    isBookmarked: Boolean,
    onClose: () -> Unit,
    onBookmark: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
        )
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            val cutoutPadding = 16.dp
            val cutoutRadius = 16.dp
            val cutoutContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9F)
            val cutoutContainerContentColor = MaterialTheme.colorScheme.onBackground
            val cutoutContainerShape = RoundedCornerShape(cutoutRadius)

            Surface(
                modifier = Modifier.fillMaxWidth().aspectRatio(0.75F),
                color = MaterialTheme.colorScheme.background,
                shape = CutOutShape(
                    radius = cutoutRadius,
                    padding = cutoutPadding
                )
            ) { }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(cutoutPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = cutoutContainerShape,
                    color = cutoutContainerColor,
                    contentColor = cutoutContainerContentColor,
                    modifier = Modifier
                        .size(min(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight))
                        .clip(cutoutContainerShape)
                        .onClick {
                            onClose()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = stringResource(SharedRes.strings.back),
                        modifier = Modifier.padding(8.dp).size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                Surface(
                    shape = cutoutContainerShape,
                    color = cutoutContainerColor,
                    contentColor = cutoutContainerContentColor,
                    modifier = Modifier
                        .size(min(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight))
                        .clip(cutoutContainerShape)
                        .onClick {
                            onBookmark()
                        }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = stringResource(SharedRes.strings.bookmark),
                        modifier = Modifier.padding(8.dp).size(24.dp),
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else LocalContentColor.current
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(cutoutPadding),
                color = cutoutContainerColor,
                contentColor = cutoutContainerContentColor,
                shape = cutoutContainerShape
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(56.dp).clip(MaterialTheme.shapes.small)
                    ) {
                        KamelImage(
                            resource = asyncPainterResource(product.imageUrl ?: String()),
                            contentDescription = product.productNameAndLanguage.first,
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                            contentScale = ContentScale.Inside
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = product.productNameAndLanguage.first,
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
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(MaterialTheme.colorScheme.background)
                .safeDrawingPadding()
        ) {
            content()
        }
    }
}
