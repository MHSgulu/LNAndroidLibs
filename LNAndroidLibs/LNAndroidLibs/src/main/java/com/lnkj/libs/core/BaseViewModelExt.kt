package com.lnkj.libs.core

import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.net.code
import com.lnkj.libs.net.msg
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import rxhttp.toDownload
import rxhttp.toFlow
import rxhttp.toFlowOkResponse
import rxhttp.toOkResponse
import rxhttp.wrapper.entity.Progress
import rxhttp.wrapper.param.*

suspend fun BaseViewModel.okRequest(
    url: String,
    vararg params: Pair<String, Any?>,
    onStart: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (msg: String, code: String) -> Unit = {msg, code -> }
) : Any{
    return RxHttp.postForm(url)
        .addAll(params.toMap())
        .toFlowResponse<String>()
        .onStart { onStart() }
        .catch {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        }.collect {
            onSuccess()
        }
}

suspend inline fun <reified T : Any> BaseViewModel.request(
    url: String,
    vararg params: Pair<String, Any?>,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: T) -> Unit,
    crossinline onError: (msg: String, code: String) -> Unit
) {
    RxHttp.postForm(url)
        .addAll(params.toMap())
        .toFlowResponse<T>()
        .onStart { onStart() }
        .catch {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        }.collect {
            onSuccess(it)
        }
}

suspend inline fun <reified T : Any> BaseViewModel.requestList(
    url: String,
    vararg params: Pair<String, Any?>,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: MutableList<T>) -> Unit,
    crossinline onError: (msg: String, code: String) -> Unit
) {
    RxHttp.postForm(url)
        .addAll(params.toMap())
        .toFlowResponse<MutableList<T>>()
        .onStart { onStart() }
        .catch {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        }.collect {
            onSuccess(it)
        }
}

suspend inline fun BaseViewModel.downloadFile(
    url: String,
    destPath: String,
    crossinline onProgress: (progress: Progress) -> Unit,
    crossinline onError: () -> Unit,
    crossinline onSuccess: (path: String) -> Unit,
){
    RxHttp.get(url)
        .toFlow(destPath){
            // 进度回调
            onProgress(it)
        }.catch {
            // 下载失败回调
            onError()
        }.collect {
            // 下载成功回调
            onSuccess(destPath)
        }

}