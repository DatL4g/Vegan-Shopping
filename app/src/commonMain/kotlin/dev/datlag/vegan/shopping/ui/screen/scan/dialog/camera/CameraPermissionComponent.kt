package dev.datlag.vegan.shopping.ui.screen.scan.dialog.camera

import dev.datlag.vegan.shopping.ui.dialog.DialogComponent

interface CameraPermissionComponent : DialogComponent {

    val showRational: Boolean

    fun request()
}