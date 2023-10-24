package dev.datlag.vegan.shopping.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

inline fun Modifier.ifTrue(predicate: Boolean, builder: Modifier.() -> Modifier) = then(if (predicate) builder() else Modifier)
inline fun Modifier.ifFalse(predicate: Boolean, builder: Modifier.() -> Modifier) = then(if (!predicate) builder() else Modifier)

@Composable
fun Number.toDp(density: Density = LocalDensity.current): Dp = with(density) {
    this@toDp.toFloat().toDp()
}

@Composable
fun Rect.toDpSize(density: Density = LocalDensity.current): DpSize = with(density) {
    DpSize(this@toDpSize.width.toDp(), this@toDpSize.height.toDp())
}

@Composable
fun <T> rememberComposable(block: @Composable () -> T & Any): T & Any {
    var rememberedItem by remember { mutableStateOf<T?>(null) }
    if (rememberedItem == null) {
        rememberedItem = block()
    }
    return rememberedItem!!
}

@Composable
fun <T> rememberComposable(key1: Any, block: @Composable () -> T & Any): T & Any {
    var rememberedItem by remember(key1) { mutableStateOf<T?>(null) }
    if (rememberedItem == null) {
        rememberedItem = block()
    }
    return rememberedItem!!
}

@Composable
fun <T> rememberComposable(key1: Any?, key2: Any?, block: @Composable () -> T & Any): T & Any {
    var rememberedItem by remember(key1, key2) { mutableStateOf<T?>(null) }
    if (rememberedItem == null) {
        rememberedItem = block()
    }
    return rememberedItem!!
}

@Composable
fun <T> rememberComposable(key1: Any?, key2: Any?, key3: Any?, block: @Composable () -> T & Any): T & Any {
    var rememberedItem by remember(key1, key2, key3) { mutableStateOf<T?>(null) }
    if (rememberedItem == null) {
        rememberedItem = block()
    }
    return rememberedItem!!
}

@Composable
fun <T> rememberComposable(vararg keys: Any?, block: @Composable () -> T & Any): T & Any {
    var rememberedItem by remember(keys) { mutableStateOf<T?>(null) }
    if (rememberedItem == null) {
        rememberedItem = block()
    }
    return rememberedItem!!
}