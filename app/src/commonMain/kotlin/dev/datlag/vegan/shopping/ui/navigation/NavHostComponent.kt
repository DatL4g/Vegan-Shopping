package dev.datlag.vegan.shopping.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import dev.datlag.vegan.shopping.ui.screen.profile.create.CreateProfileScreenComponent
import dev.datlag.vegan.shopping.ui.screen.scan.ScanScreenComponent
import dev.datlag.vegan.shopping.ui.screen.welcome.WelcomeScreenComponent
import org.kodein.di.DI

class NavHostComponent private constructor(
    componentContext: ComponentContext,
    override val di: DI
) : Component, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = ScreenConfig.Welcome,
        childFactory = ::createScreenComponent
    )

    private fun createScreenComponent(
        screenConfig: ScreenConfig,
        componentContext: ComponentContext
    ) : Component {
        return when (screenConfig) {
            is ScreenConfig.Welcome -> WelcomeScreenComponent(
                componentContext,
                di
            ) { goToCreateProfile(true) }
            is ScreenConfig.Scan -> ScanScreenComponent(
                componentContext,
                di
            )
            is ScreenConfig.Profile -> {
                when (screenConfig) {
                    is ScreenConfig.Profile.Create -> CreateProfileScreenComponent(
                        componentContext,
                        di
                    ) { goToScan(true) }
                }
            }
        }
    }

    @Composable
    override fun render() {
        Children(
            stack = stack,
            animation = stackAnimation(fade())
        ) {
            it.instance.render()
        }
    }

    private fun goToCreateProfile(replace: Boolean) {
        if (replace) {
            navigation.replaceCurrent(ScreenConfig.Profile.Create)
        } else {
            navigation.push(ScreenConfig.Profile.Create)
        }
    }

    private fun goToScan(replace: Boolean) {
        if (replace) {
            navigation.replaceCurrent(ScreenConfig.Scan)
        } else {
            navigation.push(ScreenConfig.Scan)
        }
    }

    companion object {
        fun create(componentContext: ComponentContext, di: DI) = NavHostComponent(componentContext, di)
    }
}