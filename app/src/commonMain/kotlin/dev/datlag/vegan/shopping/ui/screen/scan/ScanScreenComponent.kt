package dev.datlag.vegan.shopping.ui.screen.scan

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.*
import com.arkivanov.decompose.value.Value
import dev.datlag.vegan.shopping.common.ioDispatcher
import dev.datlag.vegan.shopping.common.launchIO
import dev.datlag.vegan.shopping.model.Barcode
import dev.datlag.vegan.shopping.model.state.OFFAction
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.network.state.OFFProductStateMachine
import dev.datlag.vegan.shopping.ui.dialog.DialogComponent
import dev.datlag.vegan.shopping.ui.screen.scan.dialog.camera.CameraPermissionDialogComponent
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.kodein.di.DI
import org.kodein.di.instance
import java.util.Locale

class ScanScreenComponent(
    componentContext: ComponentContext,
    override val di: DI
) : ScanComponent, ComponentContext by componentContext {

    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val _dialog = childSlot(
        source = dialogNavigation
    ) { config, slotContext ->
        when (config) {
            is DialogConfig.CameraPermission -> CameraPermissionDialogComponent(
                componentContext = slotContext,
                showRational = config.showRational,
                onRequest = config.onRequest,
                di = di,
                onDismissed = dialogNavigation::dismiss
            ) as DialogComponent
        }
    }
    override val dialog: Value<ChildSlot<DialogConfig, DialogComponent>> = _dialog

    private val productStateMachine: OFFProductStateMachine by di.instance()
    override val productState: Flow<OFFRequest> = productStateMachine.state.flowOn(ioDispatcher())

    @Composable
    override fun render() {
        ScanScreen(this)
    }

    override fun showDialog(config: DialogConfig) {
        dialogNavigation.activate(config) {
            Napier.e("Show finished: $config")
        }
    }

    override fun dismissDialog() {
        dialogNavigation.dismiss()
    }

    override fun loadBarcode(barcode: Barcode) = launchIO {
        productStateMachine.dispatch(OFFAction.Load(barcode, Locale.ENGLISH.toLanguageTag()))
    }
}