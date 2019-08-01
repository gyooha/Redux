package io.seroo.redux

typealias Reducer <S> = (S, Action) -> S
typealias Subscribe <S> = (S) -> Unit
typealias UnSubscribe = () -> Unit

interface Action
interface State

interface Store<S : State> {
    fun dispatch(action: Action)
    fun subscribe(listener: Subscribe<S>): UnSubscribe
    fun getState(): S
    fun replaceReducer(nextReducer: Reducer<S>)
    fun replaceState(preloadedState: S)
}