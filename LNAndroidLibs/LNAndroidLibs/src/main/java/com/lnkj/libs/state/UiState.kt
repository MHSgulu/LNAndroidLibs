package com.lnkj.libs.state

sealed class UiState<T: Any>{
    object Start : UiState<Any>()
    class Error(val msg: String, val code: String) : UiState<Any>()
    class Next<T: Any>(val t: T) : UiState<T>()
}
