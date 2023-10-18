package dev.datlag.vegan.shopping.common

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

inline fun Modifier.ifTrue(predicate: Boolean, builder: Modifier.() -> Modifier) = then(if (predicate) builder() else Modifier)
inline fun Modifier.ifFalse(predicate: Boolean, builder: Modifier.() -> Modifier) = then(if (!predicate) builder() else Modifier)