package dev.datlag.vegan.shopping.ui.screen.welcome

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI

class WelcomeScreenComponent(
    componentContext: ComponentContext,
    override val di: DI,
    private val onFinish: () -> Unit
) : WelcomeComponent, ComponentContext by componentContext {

    @Composable
    override fun render() {
        WelcomeScreen(this)
    }

    override fun finish() {
        onFinish()
    }
}