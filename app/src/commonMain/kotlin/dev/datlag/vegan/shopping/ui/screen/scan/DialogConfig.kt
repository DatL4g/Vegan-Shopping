package dev.datlag.vegan.shopping.ui.screen.scan

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.datlag.vegan.shopping.model.openfoodfacts.Product

@Parcelize
sealed class DialogConfig : Parcelable {

    @Parcelize
    data class CameraPermission(
        val showRational: Boolean,
        val onRequest: () -> Unit
    ) : DialogConfig()

    @Parcelize
    data class ProductType(
        val product: Product
    ) : DialogConfig()
}