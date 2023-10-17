package dev.datlag.vegan.shopping.common

import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODABAR
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_39
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_93
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_128
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_EAN_8
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_EAN_13
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_ITF
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_A
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_E
import dev.datlag.vegan.shopping.model.Barcode


fun com.google.mlkit.vision.barcode.common.Barcode.toMPBarcode(): Barcode? {
    val type = when (this.format) {
        FORMAT_CODABAR -> Barcode.TYPE.CODEBAR
        FORMAT_CODE_39 -> Barcode.TYPE.CODE_39
        FORMAT_CODE_93 -> Barcode.TYPE.CODE_93
        FORMAT_CODE_128 -> Barcode.TYPE.CODE_128
        FORMAT_EAN_8 -> Barcode.TYPE.EAN_8
        FORMAT_EAN_13 -> Barcode.TYPE.EAN_13
        FORMAT_ITF -> Barcode.TYPE.ITF
        FORMAT_UPC_A -> Barcode.TYPE.UPC_A
        FORMAT_UPC_E -> Barcode.TYPE.UPC_E
        else -> null
    }
    val data = this.rawValue ?: this.rawBytes?.let { b -> String(b) } ?: this.displayValue

    return if (type != null && data != null) {
        Barcode(type, data)
    } else {
        null
    }
}