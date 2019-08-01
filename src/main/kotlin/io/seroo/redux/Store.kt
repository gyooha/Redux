package io.seroo.redux

class DefaultStore<S : State>(
    preloadedState: S,
    preloadedReducer: Reducer<S>
) : Store<S> {
    private var isDispatching = false
    private val subscribes: MutableSet<Subscribe<S>> = mutableSetOf()

    private var currentState: S = preloadedState
    private var currentReducer: Reducer<S> = preloadedReducer

    override fun dispatch(action: Action) {
        try {
            isDispatching = true
            currentState = currentReducer(currentState, action)
        } finally {
            isDispatching = false
        }

        subscribes.forEach {
            it(currentState)
        }
    }

    override fun subscribe(listener: Subscribe<S>): UnSubscribe {
        if (isDispatching) throw Error("You may not call store.getCurrentState() while the Reducer is executing.")
        var isSubscribed = true
        return {
            if (!isSubscribed) Unit
            if (isDispatching) throw Error("You may not call store.getCurrentState() while the Reducer is executing.")

            isSubscribed = false
            subscribes.remove(listener)
        }
    }

    override fun getState(): S {
        if (isDispatching) throw Error("You may not call store.getCurrentState() while the Reducer is executing.")

        return currentState
    }

    override fun replaceState(preloadedState: S) {
        currentState = preloadedState
    }

    override fun replaceReducer(nextReducer: (S, Action) -> S) {
        currentReducer = nextReducer
    }
}