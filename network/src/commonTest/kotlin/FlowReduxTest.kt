import app.cash.turbine.test
import dev.datlag.vegan.shopping.model.state.OFFAction
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.network.state.OFFProductStateMachine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class FlowReduxTest {

    @Test
    fun `state machine starts with Waiting state`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
        }
    }

    @Test
    fun `move from Waiting to Loading state`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
        }
    }

    @Test
    fun `move from Loading to Success state on successful http response`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Success>(awaitItem())
        }
    }

    @Test
    fun `move from Loading to Error state on error http response`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = ""
        assert(barcode.isEmpty()) { "Barcode is not empty, can't reach error state" }
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Error>(awaitItem())
        }
    }

    @Test
    fun `move from Loading to Waiting state on close`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            stateMachine.dispatch(OFFAction.Close)
            assertEquals(OFFRequest.WAITING, awaitItem())
        }
    }

    @Test
    fun `move from Success to Loading state with info about previous Product`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val barcode2 = "5449000214911"
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Success>(awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode2, language = language))
            val loadingItem = awaitItem()
            assertIs<OFFRequest.Loading>(loadingItem)
            assertNotNull(loadingItem.previousProduct) { "Switched to new Loading state but the previous product is null" }
        }
    }

    @Test
    fun `move from Success to Loading then Error state with info about previous Product`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val barcode2 = ""
        assert(barcode2.isEmpty()) { "Barcode 2 is not empty, can't reach error state" }
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Success>(awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode2, language = language))
            val loadingItem = awaitItem()
            assertIs<OFFRequest.Loading>(loadingItem)
            assertNotNull(loadingItem.previousProduct) { "Switched to new Loading state but the previous product is null" }
            val errorItem = awaitItem()
            assertIs<OFFRequest.Error>(errorItem)
            assertNotNull(errorItem.previousProduct) { "Switched to Error state but the previous product is null" }
        }
    }

    @Test
    fun `stay on Success state after calling Load with same barcode`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = "4037400344294"
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Success>(awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `stay on Error state after calling Load with same barcode`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = ""
        assert(barcode.isEmpty()) { "Barcode is not empty, can't reach error state" }
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Error>(awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `move from Error to Loading state after calling Retry`() = runTest {
        val stateMachine = OFFProductStateMachine(KtorfitTest.createOpenFoodFactsApi())
        val barcode = ""
        assert(barcode.isEmpty()) { "Barcode is not empty, can't reach error state" }
        val language = "en"
        stateMachine.state.test {
            assertEquals(OFFRequest.WAITING, awaitItem())
            stateMachine.dispatch(OFFAction.Load(barcode = barcode, language = language))
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
            assertIs<OFFRequest.Error>(awaitItem())
            stateMachine.dispatch(OFFAction.Retry)
            assertEquals(OFFRequest.Loading(
                barcode = barcode,
                language = language,
                previousProduct = null
            ), awaitItem())
        }
    }
}