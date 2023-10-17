package dev.datlag.vegan.shopping.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.backHandler
import com.arkivanov.essenty.instancekeeper.instanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.arkivanov.essenty.statekeeper.stateKeeper
import dev.datlag.vegan.shopping.App
import dev.datlag.vegan.shopping.R
import dev.datlag.vegan.shopping.ui.App
import dev.datlag.vegan.shopping.ui.LocalLifecycleOwner
import dev.datlag.vegan.shopping.ui.navigation.NavHostComponent
import io.kamel.core.config.*
import io.kamel.image.config.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            this.setTheme(R.style.AppTheme)
        } else {
            installSplashScreen()
        }
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        val di = ((applicationContext as? App) ?: (application as App)).di
        val imageConfig = KamelConfig {
            takeFrom(KamelConfig.Default)
            resourcesFetcher(this@MainActivity)
            resourcesIdMapper(this@MainActivity)
            imageVectorDecoder()
            svgDecoder()
        }

        val lifecycleOwner = object : LifecycleOwner {
            override val lifecycle: Lifecycle = essentyLifecycle()
        }
        val root = NavHostComponent.create(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycleOwner.lifecycle,
                stateKeeper = stateKeeper(),
                instanceKeeper = instanceKeeper(),
                backHandler = backHandler()
            ),
            di = di
        )

        setContent {
            CompositionLocalProvider(
                LocalKamelConfig provides imageConfig,
                LocalLifecycleOwner provides lifecycleOwner
            ) {
                App(di) {
                    root.render()
                }
            }
        }
    }
}