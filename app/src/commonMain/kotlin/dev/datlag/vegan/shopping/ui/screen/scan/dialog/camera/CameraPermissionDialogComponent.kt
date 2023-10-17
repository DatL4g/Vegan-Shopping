package dev.datlag.vegan.shopping.ui.screen.scan.dialog.camera

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI

class CameraPermissionDialogComponent(
    componentContext: ComponentContext,
    override val showRational: Boolean,
    private val onRequest: () -> Unit,
    override val di: DI,
    private val onDismissed: () -> Unit
) : CameraPermissionComponent, ComponentContext by componentContext {

    @Composable
    override fun render() {
        CameraPermissionDialog(this)
    }

    override fun dismiss() {
        onDismissed()
    }

    override fun request() {
        onRequest()
    }
}