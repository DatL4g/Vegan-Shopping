package dev.datlag.vegan.shopping.ui.screen.scan

import androidx.camera.core.CameraSelector
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import dev.datlag.vegan.shopping.common.ioDispatcher
import dev.datlag.vegan.shopping.common.observeValue
import dev.datlag.vegan.shopping.common.toMPBarcode
import kotlinx.coroutines.asExecutor

@Composable
actual fun CameraScan(component: ScanComponent) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember { LifecycleCameraController(context) }

    AndroidView(
        factory = {
            val previewView = PreviewView(context)
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            cameraController.cameraSelector = cameraSelector
            cameraController.bindToLifecycle(lifecycleOwner)
            previewView.controller = cameraController

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )

    val zoomState = cameraController.cameraInfo?.zoomState?.observeValue(null)
    val zoomCallback = remember(cameraController) {
        ZoomSuggestionOptions.ZoomCallback {
            cameraController.setZoomRatio(it)
            true
        }
    }
    val zoomOptions = remember(zoomCallback, zoomState) {
        val builder = ZoomSuggestionOptions.Builder(zoomCallback)
        val zoomMaxRatio = zoomState?.maxZoomRatio
        if (zoomMaxRatio != null) {
            builder.setMaxSupportedZoomRatio(zoomMaxRatio)
        }
        builder.build()
    }

    val barcodeOptions = remember(zoomOptions) {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_CODABAR,
                Barcode.FORMAT_CODE_39,
                Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_ITF,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E
            ).setZoomSuggestionOptions(zoomOptions)
            .setExecutor(ioDispatcher().asExecutor())
            .build()
    }

    val barcodeScanner = remember(barcodeOptions) {
        BarcodeScanning.getClient(barcodeOptions)
    }

    LaunchedEffect(cameraController) {
        cameraController.setImageAnalysisAnalyzer(
            ioDispatcher().asExecutor(),
            MlKitAnalyzer(
                listOf(
                    barcodeScanner
                ),
                CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
                ioDispatcher().asExecutor()
            ) { result ->
                val foundBarcodes = result.getValue(barcodeScanner)?.mapNotNull {
                    it.toMPBarcode()
                } ?: emptyList()

                foundBarcodes.firstOrNull()?.let {
                    component.loadBarcode(it)
                }
            }
        )
    }
}