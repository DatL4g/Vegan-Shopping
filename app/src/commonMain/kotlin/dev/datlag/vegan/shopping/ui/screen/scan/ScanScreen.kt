package dev.datlag.vegan.shopping.ui.screen.scan

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.datlag.vegan.shopping.ui.custom.permission.CameraPermission
import dev.datlag.vegan.shopping.ui.screen.scan.component.ProductInfo

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScanScreen(component: ScanComponent) {
    val dialogState by component.dialog.subscribeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
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