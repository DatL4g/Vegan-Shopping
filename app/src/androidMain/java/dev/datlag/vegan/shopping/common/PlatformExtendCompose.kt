package dev.datlag.vegan.shopping.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dev.icerock.moko.resources.FontResource

actual fun FontResource.toComposeFont(
    weight: FontWeight,
    style: FontStyle
): Font {
    return Font(
        resId = this.fontResourceId,
        weight = weight,
        style = style
    )
}

@OptIn(ExperimentalFoundationApi::class)
actual fun Modifier.onClick(
    enabled: Boolean,
    onDoubleClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onClick: () -> Unit
): Modifier {
    return this.combinedClickable(
        enabled = enabled,
        onDoubleClick = onDoubleClick,
        onLongClick = onLongClick,
        onClick = onClick
    )
}

@Composable
fun <T> LiveData<T>.observeAsState() = observeAsState(value)

@Composable
fun <R, T : R> LiveData<T>.observeAsState(initial: R): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember {
        @Suppress("UNCHECKED_CAST") /* Initialized values of a LiveData<T> must be a T */
        (mutableStateOf(if (isInitialized) value as T else initial))
    }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}

@Composable
fun <T> LiveData<T>.observeValue() = observeAsState().value

@Composable
fun <R, T : R> LiveData<T>.observeValue(initial: R): R = observeAsState(initial).value