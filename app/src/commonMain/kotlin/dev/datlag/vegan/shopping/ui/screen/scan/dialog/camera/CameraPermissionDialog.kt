package dev.datlag.vegan.shopping.ui.screen.scan.dialog.camera

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import dev.datlag.vegan.shopping.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CameraPermissionDialog(component: CameraPermissionComponent) {
    AlertDialog(
        onDismissRequest = {
            component.dismiss()
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Camera,
                contentDescription = stringResource(SharedRes.strings.camera)
            )
        },
        title = {
            Text(
                text = stringResource(SharedRes.strings.camera_permission_title),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            if (component.showRational) {
                Text(
                    text = stringResource(SharedRes.strings.camera_permission_text_rational),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = stringResource(SharedRes.strings.camera_permission_text),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    component.request()
                    component.dismiss()
                }
            ) {
                Text(text = stringResource(SharedRes.strings.allow))
            }
        }
    )
}