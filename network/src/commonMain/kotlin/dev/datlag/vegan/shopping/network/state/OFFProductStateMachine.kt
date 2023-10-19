package dev.datlag.vegan.shopping.network.state

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import dev.datlag.vegan.shopping.model.openfoodfacts.Product
import dev.datlag.vegan.shopping.model.state.OFFAction
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.network.OpenFoodFactsAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalCoroutinesApi::class)
class OFFProductStateMachine(
    private val api: OpenFoodFactsAPI
) : FlowReduxStateMachine<OFFRequest, OFFAction>(initialState = OFFRequest.WAITING) {

    private val previousProduct: MutableStateFlow<Product?> = MutableStateFlow(null)

    init {
        spec {
            inState<OFFRequest.WAITING> {
                onEnterEffect {
                    previousProduct.emit(null)
                }
                on<OFFAction.Load> { action, state ->
                    state.override {
                        OFFRequest.Loading(action.barcode, action.language, null)
                    }
                }
            }
            inState<OFFRequest.Loading> {
                onEnter { state ->
                    try {
                        val product = api.product(state.snapshot.barcode, state.snapshot.language)
                        state.override {
                            OFFRequest.Success(state.snapshot.barcode, product)
                        }
                    } catch (t: Throwable) {
                        state.override {
                            OFFRequest.Error(
                                state.snapshot.barcode,
                                state.snapshot.language,
                                state.snapshot.previousProduct ?: this@OFFProductStateMachine.previousProduct.value,
                                t.message ?: String()
                            )
                        }
                    }
                }
                on<OFFAction.Close> { _, state ->
                    state.override { OFFRequest.WAITING }
                }
            }
            inState<OFFRequest.Success> {
                onEnterEffect { state ->
                    previousProduct.emit(state.product.product)
                }
                on<OFFAction.Load> { action, state ->
                    if (!action.barcode.equals(state.snapshot.barcode, true)) {
                        state.override {
                            OFFRequest.Loading(
                                action.barcode,
                                action.language,
                                state.snapshot.product.product
                            )
                        }
                    } else {
                        state.noChange()
                    }
                }
                on<OFFAction.Close> { _, state ->
                    state.override { OFFRequest.WAITING }
                }
            }
            inState<OFFRequest.Error> {
                on<OFFAction.Load> { action, state ->
                    if (!action.barcode.equals(state.snapshot.barcode, true)) {
                        state.override {
                            OFFRequest.Loading(
                                action.barcode,
                                action.language,
                                state.snapshot.previousProduct ?: this@OFFProductStateMachine.previousProduct.value
                            )
                        }
                    } else {
                        state.noChange()
                    }
                }
                on<OFFAction.Retry> { _, state ->
                    state.override {
                        OFFRequest.Loading(
                            state.snapshot.barcode,
                            state.snapshot.language,
                            state.snapshot.previousProduct ?: this@OFFProductStateMachine.previousProduct.value
                        )
                    }
                }
                on<OFFAction.Close> { _, state ->
                    state.override { OFFRequest.WAITING }
                }
            }
        }
    }
}