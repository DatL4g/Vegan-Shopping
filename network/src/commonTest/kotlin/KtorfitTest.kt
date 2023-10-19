import de.jensklingenberg.ktorfit.ktorfit
import dev.datlag.vegan.shopping.model.openfoodfacts.ProductResponse
import dev.datlag.vegan.shopping.network.OpenFoodFactsAPI
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertIs
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.fail

class KtorfitTest {

    @Test
    fun testCreateOpenFoodFactsApi() {
        assertIs<OpenFoodFactsAPI>(createOpenFoodFactsApi(), "Ktorfit did not generate OpenFoodFacts API")
    }

    @Test
    fun testCallOpenFoodFactsApi() = runTest {
        val api = createOpenFoodFactsApi()
        val product = try {
            api.product("4037400344294")
        } catch (e: Throwable) {
            fail("Ktorfit could not call OpenFoodFacts API", e)
        }
        assertIs<ProductResponse>(product, "Ktorfit could not call OpenFoodFacts API")
    }


    companion object {
        val jsonObject = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        fun createOpenFoodFactsApi(): OpenFoodFactsAPI {
            return ktorfit {
                baseUrl("https://world.openfoodfacts.org/api/v3/")
                httpClient(CIO) {
                    install(ContentNegotiation) {
                        json(jsonObject, ContentType.Application.Json)
                        json(jsonObject, ContentType.Text.Plain)
                    }
                }
            }.create<OpenFoodFactsAPI>()
        }
    }
}