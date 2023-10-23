package dev.datlag.vegan.shopping.common

import androidx.compose.runtime.Composable
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