package dev.datlag.vegan.shopping.ui.screen.scan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.datlag.vegan.shopping.ui.custom.permission.CameraPermission
import dev.datlag.vegan.shopping.ui.screen.scan.component.ProductInfo

@Composable
fun ScanScreen(component: ScanComponent) {
    val dialogState by component.dialog.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPermission(
            onGranted = {
                component.dismissDialog()
                CameraScan(component)
            },
            onShowInfo = { state ->
                component.showDialog(
                    DialogConfig.CameraPermission(
                        showRational = state.showRationale,
                        onRequest = state::request
                    )
                )
            },
            onDeniedForever = { state ->
                component.showDialog(
                    DialogConfig.CameraPermission(
                        showRational = state.showRationale,
                        onRequest = state::manually
                    )
                )
            }
        )
        ProductInfo(component)
    }
    dialogState.child?.instance?.render()
}