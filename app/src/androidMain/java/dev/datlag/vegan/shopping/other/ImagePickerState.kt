package dev.datlag.vegan.shopping.other

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap

actual data class ImagePickerState(
    private val mediaPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    actual fun launch() {
        mediaPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}