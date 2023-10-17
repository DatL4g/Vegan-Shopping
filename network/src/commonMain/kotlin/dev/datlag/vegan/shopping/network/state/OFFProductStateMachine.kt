package dev.datlag.vegan.shopping.network.state

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import dev.datlag.vegan.shopping.model.state.OFFAction
import dev.datlag.vegan.shopping.model.state.OFFRequest
import dev.datlag.vegan.shopping.network.OpenFoodFactsAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class OFFProductStateMachine(
    private val api: OpenFoodFactsAPI
) : FlowReduxStateMachine<OFFRequest, OFFAction>(initialState = OFFRequest.WAITING) {
    init {
        spec {
            inState<OFFRequest.WAITING> {
                on<OFFAction.Load> { action, state ->
                    state.override {
                        OFFRequest.Loading(action.barcode, action.language)
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
                            OFFRequest.Error(state.snapshot.barcode, state.snapshot.language, t.message ?: String())
                        }
                    }
                }
            }
            inState<OFFRequest.Success> {
                on<OFFAction.Load> { action, state ->
                    if (!action.barcode.equals(state.snapshot.barcode, true)) {
                        state.override { OFFRequest.Loading(action.barcode, action.language) }
                    } else {
                        state.noChange()
                    }
                }
            }
            inState<OFFRequest.Error> {
                on<OFFAction.Load> { action, state ->
                    if (!action.barcode.equals(state.snapshot.barcode, true)) {
                        state.override { OFFRequest.Loading(action.barcode, action.language) }
                    } else {
                        state.noChange()
                    }
                }
                on<OFFAction.Retry> { _, state ->
                    state.override { OFFRequest.Loading(state.snapshot.barcode, state.snapshot.language) }
                }
            }
        }
    }
}