package io.seroo.redux

object Redux {
    private lateinit var defaultStore: DefaultStore<*>
    fun <S : State> createStore(preloadedState: S, reducer: Reducer<S>) = if (Redux::defaultStore.isInitialized) {
        (defaultStore as? DefaultStore<S>)?.run {
            apply {
                replaceState(preloadedState)
                replaceReducer(reducer)
            }
        } ?: DefaultStore(preloadedState, reducer).also { defaultStore = it }
    } else {
        DefaultStore(preloadedState, reducer).also { defaultStore = it }
    }
}