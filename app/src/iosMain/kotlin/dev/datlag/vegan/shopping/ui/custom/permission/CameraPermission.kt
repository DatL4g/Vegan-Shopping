package dev.datlag.vegan.shopping.ui.custom.permission

import androidx.compose.runtime.Composable
import dev.datlag.vegan.shopping.other.PlatformPermissionState

@Composable
actual fun CameraPermission(
    onGranted: @Composable () -> Unit,
    onShowInfo: @Composable (PlatformPermissionState) -> Unit,
    onDeniedForever: @Composable (PlatformPermissionState) -> Unit
) {

}