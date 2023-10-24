package dev.datlag.vegan.shopping.ui.screen.scan.dialog.product.type

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import org.kodein.di.DI

class ProductTypeDialogComponent(
    componentContext: ComponentContext,
    override val product: Product,
    override val di: DI,
    private val onDismissed: () -> Unit
) : ProductTypeComponent, ComponentContext by componentContext {

    @Composable
    override fun render() {
        ProductTypeDialog(this)
    }

    override fun dismiss() {
        onDismissed()
    }
}