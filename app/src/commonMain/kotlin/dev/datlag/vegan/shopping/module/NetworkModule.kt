package dev.datlag.vegan.shopping.module

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfitBuilder
import dev.datlag.vegan.shopping.network.OpenFoodFactsAPI
import dev.datlag.vegan.shopping.network.state.OFFProductStateMachine
import io.ktor.client.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

data object NetworkModule {

    const val NAME = "NetworkModule"

    private const val OFF = "OpenFoodFacts"

    val di = DI.Module(NAME) {
        import(PlatformModule.di)

        bindSingleton {
            ktorfitBuilder {
                httpClient(instance<HttpClient>())
            }
        }

        bindSingleton(OFF) {
            val builder: Ktorfit.Builder = instance()
            builder.build {
                baseUrl("https://world.openfoodfacts.org/api/v3/")
            }
        }
        bindSingleton {
            val offApi: Ktorfit = instance(OFF)
            offApi.create<OpenFoodFactsAPI>()
        }

        bindSingleton {
            OFFProductStateMachine(instance())
        }
    }
}