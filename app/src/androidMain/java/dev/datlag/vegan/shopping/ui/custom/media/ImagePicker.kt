package dev.datlag.vegan.shopping.ui.custom.media

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import dev.datlag.vegan.shopping.other.ImagePickerState

@Composable
actual fun rememberImagePicker(onPick: (ImageBitmap?) -> Unit): ImagePickerState {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == imageUri) {
                onPick(bitmap)
            } else {
                imageUri = uri
            }
        }
    )
    val pickerState = remember(mediaPicker) {
        ImagePickerState(
            mediaPicker
        )
    }


    LaunchedEffect(imageUri) {
        imageUri?.let { uri ->
            bitmap = (if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }).asImageBitmap()
            onPick(bitmap)
        }
    }

    return pickerState
}