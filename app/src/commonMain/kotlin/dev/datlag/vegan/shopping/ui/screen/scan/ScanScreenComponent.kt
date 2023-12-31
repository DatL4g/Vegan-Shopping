package dev.datlag.vegan.shopping.ui.screen.scan

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import dev.datlag.vegan.shopping.common.ioDispatcher
import dev.datlag.vegan.shopping.common.launchIO
import dev.datlag.vegan.shopping.model.Barcode
import dev.datlag.vegan.shopping.model.state.OFFAction
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.network.state.OFFProductStateMachine
import dev.datlag.vegan.shopping.ui.dialog.DialogComponent
import dev.datlag.vegan.shopping.ui.screen.scan.dialog.camera.CameraPermissionDialogComponent
import dev.datlag.vegan.shopping.ui.screen.scan.dialog.product.type.ProductTypeDialogComponent
import io.github.aakira.napier.Napier
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.delay
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
            is DialogConfig.ProductType -> ProductTypeDialogComponent(
                componentContext = slotContext,
                product = config.product,
                di = di,
                onDismissed = dialogNavigation::dismiss
            )
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

    override fun dismissCameraPermissionDialog() {
        val isCameraInstance = dialog.value.child?.configuration is DialogConfig.CameraPermission
                || _dialog.value.child?.configuration is DialogConfig.CameraPermission
                || dialog.value.child?.configuration?.instanceOf(DialogConfig.CameraPermission::class) == true
                || _dialog.value.child?.configuration?.instanceOf(DialogConfig.CameraPermission::class) == true
        if (isCameraInstance) {
            dismissDialog()
        }
    }

    override fun loadBarcode(barcode: Barcode) = launchIO {
        productStateMachine.dispatch(OFFAction.Load(barcode, Locale.ENGLISH.toLanguageTag()))
    }

    override fun closeProductInfo(delay: Long) = launchIO {
        delay(delay)
        productStateMachine.dispatch(OFFAction.Close)
    }
}