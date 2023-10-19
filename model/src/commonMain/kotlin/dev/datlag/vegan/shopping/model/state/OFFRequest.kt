package dev.datlag.vegan.shopping.model.state

import dev.datlag.vegan.shopping.model.Barcode
import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import dev.datlag.vegan.shopping.model.openfoodfacts.ProductResponse
import kotlinx.serialization.json.JsonElement

sealed interface OFFRequest {
    data object WAITING : OFFRequest

    data class Loading(
        val barcode: String,
        val language: String,
        val previousProduct: Product?
    ) : OFFRequest {
        constructor(
            barcode: Barcode,
            language: String,
            previousProduct: Product?,
        ) : this(barcode.data, language, previousProduct)
    }

    data class Success(
        val barcode: String,
        val product: ProductResponse
    ) : OFFRequest

    data class Error(
        val barcode: String,
        val language: String,
        val previousProduct: Product?,
        val msg: String
    ) : OFFRequest
}

sealed interface OFFAction {
    data class Load(
        val barcode: String,
        val language: String
    ) : OFFAction {
        constructor(barcode: Barcode, language: String) : this(barcode.data, language)
    }

    data object Retry : OFFAction

    data object Close : OFFAction
}