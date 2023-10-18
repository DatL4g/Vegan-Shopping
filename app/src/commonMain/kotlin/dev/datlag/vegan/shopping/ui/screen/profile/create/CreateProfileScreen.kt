package dev.datlag.vegan.shopping.ui.screen.profile.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ChooseProfileAvatar
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ProfileNameField
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ProfileTypeField
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalSoftwareKeyboardApi::class, )
@Composable
fun CreateProfileScreen(component: CreateProfileComponent) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ChooseProfileAvatar(component)
        }
        item {
            ProfileNameField(component)
        }
        item {
            ProfileTypeField(component)
        }
        item {
            Button(
                onClick = {
                    component.create()
                }
            ) {
                Text(text = stringResource(SharedRes.strings.create))
            }
        }
    }
}

