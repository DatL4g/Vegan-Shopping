package dev.datlag.vegan.shopping.ui.screen.scan.dialog.product.type

import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import dev.datlag.vegan.shopping.ui.dialog.DialogComponent

interface ProductTypeComponent : DialogComponent {

    val product: Product
}