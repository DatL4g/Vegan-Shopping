package dev.datlag.vegan.shopping.ui.screen.welcome.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.ui.LocalDarkMode
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun FirstPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val res = if (LocalDarkMode.current) {
            SharedRes.images.shopping_cart_dark
        } else {
            SharedRes.images.shopping_cart_light
        }

        Image(
            modifier = Modifier.fillMaxWidth(0.5F),
            painter = painterResource(res),
            contentDescription = stringResource(SharedRes.strings.welcome),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = buildAnnotatedString {
                append(stringResource(SharedRes.strings.welcome_first_text_part_1).trim())
                append(" ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(stringResource(SharedRes.strings.app_name).trim())
                }
                append(stringResource(SharedRes.strings.welcome_first_text_part_2).trim())
                appendLine()
                appendLine()
                append(stringResource(SharedRes.strings.welcome_first_text_part_3).trim())
            },
            modifier = Modifier.fillMaxWidth(0.75F),
            textAlign = TextAlign.Center
        )
    }
}