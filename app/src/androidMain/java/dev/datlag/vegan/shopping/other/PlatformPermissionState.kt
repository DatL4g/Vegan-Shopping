package dev.datlag.vegan.shopping.other

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import dev.datlag.vegan.shopping.BuildConfig
import java.lang.ref.WeakReference

@Parcelize
actual data class PlatformPermissionState(
    actual val permission: String,
    actual val granted: Boolean,
    actual val showRationale: Boolean,
    private val launchRequest: () -> Unit,
) : Parcelable {
    actual fun request() {
        launchRequest()
    }

    actual fun manually() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.get()?.let {
            ContextCompat.startActivity(it, intent, null)
        }
    }

    companion object {
        private var context: WeakReference<Context?> = WeakReference(null)

        @OptIn(ExperimentalPermissionsApi::class)
        fun fromAccompanist(state: PermissionState, context: Context): PlatformPermissionState {
            this.context.clear()
            this.context = WeakReference(context)

            return PlatformPermissionState(
                permission = state.permission,
                granted = state.status.isGranted,
                showRationale = state.status.shouldShowRationale
            ) {
                state.launchPermissionRequest()
            }
        }
    }
}