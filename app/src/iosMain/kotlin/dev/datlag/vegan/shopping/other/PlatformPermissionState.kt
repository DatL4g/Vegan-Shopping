package dev.datlag.vegan.shopping.other

actual data class PlatformPermissionState(
    actual val permission: String
) {
    actual val granted: Boolean
        get() = false
    actual val showRationale: Boolean
        get() = false

    actual fun request() {
    }

    actual fun manually() {
    }
}