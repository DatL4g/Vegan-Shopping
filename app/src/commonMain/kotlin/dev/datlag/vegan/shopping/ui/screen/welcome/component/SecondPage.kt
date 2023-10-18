package dev.datlag.vegan.shopping.ui.screen.welcome.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.ui.LocalDarkMode
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SecondPage(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val res = if (LocalDarkMode.current) {
            SharedRes.images.cooking_dark
        } else {
            SharedRes.images.cooking_light
        }

        Image(
            modifier = Modifier.fillMaxWidth(0.5F),
            painter = painterResource(res),
            contentDescription = stringResource(SharedRes.strings.welcome_second),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = buildAnnotatedString {
                append(stringResource(SharedRes.strings.welcome_second_text_part_1).trim())
                appendLine()
                appendLine()
                append(stringResource(SharedRes.strings.welcome_second_text_part_2).trim())
            },
            modifier = Modifier.fillMaxWidth(0.75F),
            textAlign = TextAlign.Center
        )
    }
}