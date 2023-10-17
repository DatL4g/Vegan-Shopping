package dev.datlag.vegan.shopping.ui.screen.profile.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.ifTrue
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.common.onClick
import dev.datlag.vegan.shopping.ui.custom.Avatar
import dev.datlag.vegan.shopping.ui.custom.media.rememberImagePicker
import dev.datlag.vegan.shopping.ui.screen.profile.create.CreateProfileComponent
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseProfileAvatar(
    component: CreateProfileComponent
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val appAvatar by component.appAvatar.collectAsStateWithLifecycle()
        val useAppAvatar by component.useAppAvatar.collectAsStateWithLifecycle()
        val pickerState = rememberImagePicker {
            component.loadAvatar(it)
        }

        Box(
            modifier = Modifier.fillMaxWidth(0.5F).aspectRatio(1F),
            contentAlignment = Alignment.Center
        ) {
            val otherAvatar by component.otherAvatar.collectAsStateWithLifecycle()

            if (otherAvatar == null || useAppAvatar) {
                Image(
                    painter = painterResource(appAvatar.getResource()),
                    contentDescription = stringResource(SharedRes.strings.avatar),
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Image(
                    bitmap = otherAvatar!!,
                    contentDescription = stringResource(SharedRes.strings.avatar),
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }

            Image(
                imageVector = Icons.Default.AddPhotoAlternate,
                contentDescription = stringResource(SharedRes.strings.choose_image),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(max(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight))
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25F))
                    .ifTrue(otherAvatar != null) {
                        border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    }.onClick {
                        pickerState.launch()
                    },
                alignment = Alignment.Center,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }


        FlowRow(
            modifier = Modifier.fillMaxWidth(0.75F),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            maxItemsInEachRow = 4
        ) {
            repeat(4) { iteration ->
                val avatar = when (iteration) {
                    0 -> Avatar.Female.One
                    1 -> Avatar.Female.Two
                    2 -> Avatar.Male.One
                    else -> Avatar.Male.Two
                }

                Image(
                    painter = painterResource(avatar.getResource()),
                    contentDescription = stringResource(SharedRes.strings.avatar),
                    modifier = Modifier
                        .size(max(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight))
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25F))
                        .ifTrue(useAppAvatar && avatar == appAvatar) {
                            border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                        }.onClick {
                            component.loadAvatar(avatar)
                        },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }

}
