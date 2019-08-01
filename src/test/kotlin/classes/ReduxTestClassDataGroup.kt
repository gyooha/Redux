package classes

import io.seroo.redux.Action
import io.seroo.redux.Reducer
import io.seroo.redux.State


data class TestState1(
        val id: String = "",
        val name: String = "",
        val address: String = "",
        val type: TestActions = TestActions.Init
) : State

data class TestState2(
        val id: String = "",
        val name: String = "",
        val type: TestActions2 = TestActions2.Init
) : State

sealed class TestActions : Action {
    object Init : TestActions()
    data class ReplaceName(val name: String) : TestActions()
    data class ReplaceId(val id: String) : TestActions()
    data class ReplaceAddress(val address: String) : TestActions()
}

sealed class TestActions2 : Action {
    object Init : TestActions2()
    data class ReplaceName(val name: String) : TestActions2()
    data class ReplaceId(val id: String) : TestActions2()
    data class ReplaceType(val type: TestActions2) : TestActions2()
}

val reducer: Reducer<TestState1> = { state, action ->
    when (action) {
        is TestActions.Init -> state.copy(type = action)
        is TestActions.ReplaceName -> state.copy(type = action)
        is TestActions.ReplaceId -> state.copy(type = action)
        is TestActions.ReplaceAddress -> state.copy(type = action)
    }

    state
}

val reducer2: Reducer<TestState2> = { state, action ->
    when (action) {
        is TestActions2.Init -> state.copy(type = action)
        is TestActions2.ReplaceName -> state.copy(type = action)
        is TestActions2.ReplaceId -> state.copy(type = action)
        is TestActions2.ReplaceType -> state.copy(type = action)
    }

    state
}