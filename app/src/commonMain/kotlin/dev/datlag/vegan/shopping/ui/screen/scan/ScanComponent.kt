package dev.datlag.vegan.shopping.ui.screen.scan

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import dev.datlag.vegan.shopping.model.Barcode
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.ui.dialog.DialogComponent
import dev.datlag.vegan.shopping.ui.navigation.Component
import kotlinx.coroutines.flow.Flow

interface ScanComponent : Component {

    val dialog: Value<ChildSlot<DialogConfig, DialogComponent>>
    val productState: Flow<OFFRequest>

    fun showDialog(config: DialogConfig)
    fun dismissDialog()

    fun loadBarcode(barcode: Barcode): Any?
    fun closeProductInfo(delay: Long = 0L): Any?
}