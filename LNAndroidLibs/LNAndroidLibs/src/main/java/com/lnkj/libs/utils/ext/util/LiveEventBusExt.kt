package com.lnkj.libs.utils.ext.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.core.Observable

@MainThread
inline fun <T> Observable<T>.observe(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}