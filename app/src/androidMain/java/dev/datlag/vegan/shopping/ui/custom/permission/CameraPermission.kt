package dev.datlag.vegan.shopping.ui.custom.permission

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.*
import dev.datlag.vegan.shopping.other.PlatformPermissionState
import io.github.aakira.napier.Napier

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun CameraPermission(
    onGranted: @Composable () -> Unit,
    onShowInfo: @Composable (PlatformPermissionState) -> Unit,
    onDeniedForever: @Composable (PlatformPermissionState) -> Unit
) {
    var callbackResult by remember { mutableStateOf<Boolean?>(null) }
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ) { granted ->
        callbackResult = granted
    }

    if (cameraPermissionState.status.isGranted) {
        onGranted()
    } else {
        if (callbackResult == true) {
            SideEffect {
                // Re-request permission to make sure granted state will be reached
                cameraPermissionState.launchPermissionRequest()
            }
        } else {
            val platformState = PlatformPermissionState.fromAccompanist(cameraPermissionState, LocalContext.current)

            if (callbackResult == false && !cameraPermissionState.status.shouldShowRationale) {
                onDeniedForever(platformState)
            } else {
                onShowInfo(platformState)
            }
        }
    }
}