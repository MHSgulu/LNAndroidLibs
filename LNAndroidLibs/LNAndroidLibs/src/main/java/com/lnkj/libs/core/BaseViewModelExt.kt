package com.lnkj.libs.core

import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.net.HttpUtils

suspend fun BaseViewModel.okRequest(
    url: String,
    vararg params: Pair<String, Any?>,
    method: HttpUtils.Method = HttpUtils.Method.POST,
    onStart: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (code: String, msg: String) -> Unit = { code, msg -> }
) {
    HttpUtils.request<Any>(
        url,
        *params,
        method = method,
        onStart = onStart,
        onError = onError,
        onSuccess = {
            onSuccess()
        }
    )
}

suspend inline fun <reified T : Any> BaseViewModel.request(
    url: String,
    vararg params: Pair<String, Any?>,
    method: HttpUtils.Method = HttpUtils.Method.POST,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: T) -> Unit,
    crossinline onError: (code: String, msg: String) -> Unit
) {
    HttpUtils.request(
        url,
        *params,
        method = method,
        onStart = onStart,
        onError = onError,
        onSuccess = onSuccess
    )
}