package dev.datlag.vegan.shopping.other

expect class PlatformPermissionState {
    val permission: String
    val granted: Boolean
    val showRationale: Boolean

    fun request()

    fun manually()
}