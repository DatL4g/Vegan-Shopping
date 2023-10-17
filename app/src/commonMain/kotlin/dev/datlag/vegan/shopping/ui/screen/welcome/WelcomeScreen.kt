package dev.datlag.vegan.shopping.ui.screen.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.*
import com.moriatsushi.insetsx.safeDrawingPadding
import dev.datlag.vegan.shopping.SharedRes
import dev.datlag.vegan.shopping.common.launchMain
import dev.datlag.vegan.shopping.ui.custom.PagerIndicator
import dev.datlag.vegan.shopping.ui.screen.welcome.component.FirstPage
import dev.datlag.vegan.shopping.ui.screen.welcome.component.SecondPage
import dev.datlag.vegan.shopping.ui.screen.welcome.component.ThirdPage
import dev.datlag.vegan.shopping.ui.theme.LeftRoundedShape
import dev.datlag.vegan.shopping.ui.theme.RightRoundedShape
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.Napier

@OptIn(ExperimentalSoftwareKeyboardApi::class, ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(component: WelcomeComponent) {
    val state = rememberPagerState { 3 }

    Box(Modifier.safeDrawingPadding()) {
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val contentModifier = Modifier.align(Alignment.Center).padding(bottom = 32.dp)

                when (index) {
                    0 -> FirstPage(contentModifier)
                    1 -> SecondPage(contentModifier)
                    else -> ThirdPage(contentModifier)
                }
            }
        }

        var buttonHeight by remember { mutableIntStateOf(0) }

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(bottom = 32.dp).onSizeChanged {
                buttonHeight = it.height
            },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val hasPrevious = state.currentPage > 0
            val lastPage = state.currentPage == state.pageCount - 1
            val scope = rememberCoroutineScope()

            if (hasPrevious) {
                FilledTonalButton(
                    onClick = {
                        scope.launchMain {
                            state.animateScrollToPage(
                                page = state.currentPage - 1
                            )
                        }
                    },
                    shape = RightRoundedShape(0.dp)
                ) {
                    Text(text = stringResource(SharedRes.strings.back))
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            Button(
                onClick = {
                    if (lastPage) {
                        component.finish()
                    } else {
                        scope.launchMain {
                            state.animateScrollToPage(
                                page = state.currentPage + 1
                            )
                        }
                    }
                },
                shape = LeftRoundedShape(0.dp)
            ) {
                if (lastPage) {
                    Text(text = stringResource(SharedRes.strings.start))
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        imageVector = Icons.Default.DoubleArrow,
                        contentDescription = stringResource(SharedRes.strings.start)
                    )
                } else {
                    Text(text = stringResource(SharedRes.strings.next))
                }
            }
        }
        PagerIndicator(
            state = state,
            indicatorColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp)
        )
    }
}