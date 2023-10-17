package dev.datlag.vegan.shopping.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.FontResource

expect fun FontResource.toComposeFont(
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal
): Font

expect fun Modifier.onClick(
    enabled: Boolean = true,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) : Modifier