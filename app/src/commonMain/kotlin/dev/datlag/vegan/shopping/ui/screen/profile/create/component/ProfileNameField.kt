package dev.datlag.vegan.shopping.ui.screen.profile.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.lifecycle.collectAsStateWithLifecycle
import dev.datlag.vegan.shopping.ui.screen.profile.create.CreateProfileComponent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileNameField(component: CreateProfileComponent) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PersonOutline,
            contentDescription = null
        )
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val firstName by component.firstName.collectAsStateWithLifecycle()
            val lastName by component.lastName.collectAsStateWithLifecycle()

            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    component.changeFirstName(it)
                },
                maxLines = 1,
                singleLine = true,
                label = {
                    Text(text = stringResource(SharedRes.strings.first_name))
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    component.changeLastName(it)
                },
                maxLines = 1,
                singleLine = true,
                label = {
                    Text(text = stringResource(SharedRes.strings.last_name))
                },
                placeholder = {
                    Text(text = stringResource(SharedRes.strings.optional))
                },
                supportingText = {
                    Text(text = stringResource(SharedRes.strings.optional))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}