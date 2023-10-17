package dev.datlag.vegan.shopping.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import dev.datlag.vegan.shopping.model.openfoodfacts.ProductResponse

interface OpenFoodFactsAPI {

    @Headers("Accept: application/json")
    @GET("product/{barcode}")
    suspend fun product(
        @Path("barcode") barcode: String,
        @Query("lc") language: String? = null
    ): ProductResponse
}