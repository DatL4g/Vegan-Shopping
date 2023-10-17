package dev.datlag.vegan.shopping.ui.screen.profile.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ChooseProfileAvatar
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ProfileNameField
import dev.datlag.vegan.shopping.ui.screen.profile.create.component.ProfileTypeField

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
    }
}

