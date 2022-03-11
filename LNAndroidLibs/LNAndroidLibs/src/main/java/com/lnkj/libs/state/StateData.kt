package com.lnkj.libs.state

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class StateData<out T>{
    object Loading: StateData<Nothing>()
    data class Success<out T>(val data: T?): StateData<T>()
    data class Error(val code: String, val msg: String): StateData<Nothing>()
}

class ResultBuilder<T>() {
    var onLoading: () -> Unit = {}
    var onSuccess: (data: T?) -> Unit = {}
    var onError: (code: String, msg: String) -> Unit = {code, msg ->  }
}

typealias StatefulLiveData<T> = LiveData<StateData<T>>
typealias StatefulMutableLiveData<T> = MutableLiveData<StateData<T>>

@MainThread
inline fun <T> StatefulLiveData<T>.observeState(
    owner: LifecycleOwner,
    init: ResultBuilder<T>.() -> Unit
) {
    val result = ResultBuilder<T>().apply(init)

    observe(owner) { state ->
        when (state) {
            is StateData.Loading -> result.onLoading.invoke()
            is StateData.Success -> result.onSuccess(state.data)
            is StateData.Error -> result.onError(state.code, state.msg)
        }
    }
}