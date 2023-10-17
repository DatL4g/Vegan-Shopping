package dev.datlag.vegan.shopping.model.openfoodfacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("code") val code: String,
    @SerialName("product") val product: Product
)
