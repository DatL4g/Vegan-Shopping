package dev.datlag.vegan.shopping.model

data class Barcode(
    val type: TYPE,
    val data: String
) {
    sealed interface TYPE {
        data object CODEBAR : TYPE
        data object CODE_39 : TYPE
        data object CODE_93 : TYPE
        data object CODE_128 : TYPE
        data object EAN_8 : TYPE
        data object EAN_13 : TYPE
        data object ITF : TYPE
        data object UPC_A : TYPE
        data object UPC_E : TYPE
    }
}
