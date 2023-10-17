package dev.datlag.vegan.shopping.ui.custom.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import dev.datlag.vegan.shopping.other.ImagePickerState

@Composable
actual fun rememberImagePicker(onPick: (ImageBitmap?) -> Unit): ImagePickerState {
    return ImagePickerState()
}