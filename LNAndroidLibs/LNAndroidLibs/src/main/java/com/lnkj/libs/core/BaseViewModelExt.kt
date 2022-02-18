package com.lnkj.libs.core

import androidx.lifecycle.asLiveData
import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.net.code
import com.lnkj.libs.net.msg
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import rxhttp.*
import rxhttp.wrapper.entity.Progress
import rxhttp.wrapper.param.*

suspend fun BaseViewModel.okRequest(
    url: String,
    vararg params: Pair<String, Any?>,
    onStart: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (msg: String, code: String) -> Unit = { msg, code -> }
): Any {
    return RxHttp.postForm(url)
        .addAll(params.toMap())
        .toFlowResponse<Any>()
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
) {
    RxHttp.get(url)
        .toFlow(destPath) {
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


/**
 *
 * @param url String
 * @param tag String
 * @param params Array<out Pair<String, Any?>>
 * @param onStart Function0<Unit>
 * @param onSuccess Function0<Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 * @return Any
 */
fun okRequest(
    url: String,
    tag: String,
    vararg params: Pair<String, Any?>,
    onStart: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (msg: String, code: String) -> Unit = { msg, code -> }
): Any {
    return RxHttp.postForm(url)
        .tag(tag)
        .addAll(params.toMap())
        .asResponse<Any>()
        .doOnSubscribe {
            onStart()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess()
        }, {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        })
}

/**
 *
 * @param url String
 * @param params Array<out Pair<String, Any?>>
 * @param onStart Function0<Unit>
 * @param onSuccess Function1<[@kotlin.ParameterName] T, Unit>
 * @param onError Function2<[@kotlin.ParameterName] String, [@kotlin.ParameterName] String, Unit>
 */
inline fun <reified T : Any> request(
    url: String,
    tag: String,
    vararg params: Pair<String, Any?>,
    crossinline onStart: () -> Unit,
    crossinline onSuccess: (data: T) -> Unit,
    crossinline onError: (msg: String, code: String) -> Unit
) {
    RxHttp.postForm(url)
        .tag(tag)
        .addAll(params.toMap())
        .asResponse<T>()
        .doOnSubscribe {
            onStart()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess(it)
        }, {
            val msg = it.msg
            val code = it.code
            onError(msg, code)
        })
}

/**
 * 取消指定tag的请求
 * @param tag String
 */
fun cancelRequest(tag: String) {
    RxHttpPlugins.cancelAll(tag)
}

/**
 * 取消所有请求
 */
fun cancelAllRequest() {
    RxHttpPlugins.cancelAll()
}