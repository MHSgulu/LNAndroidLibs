package com.lnkj.libs.net

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import rxhttp.toFlowProgress
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.asResponse
import rxhttp.wrapper.param.toFlowResponse


object HttpUtils {
    enum class Method{
        POST, GET, PUT, DELETE
    }

    suspend inline fun<reified T: Any> request(
        url: String,
        vararg params: Pair<String, Any?>,
        method: Method = Method.POST,
        crossinline onStart: () -> Unit,
        crossinline onSuccess: (data: T) -> Unit,
        crossinline onError: (code: String, msg: String) -> Unit
    ){
        val http = when(method){
            Method.POST -> {
                RxHttp.postForm(url)
                    .addAll(params.toMap())
            }
            Method.GET -> {
                RxHttp.get(url)
                    .addAllQuery(params.toMap())
            }
            Method.DELETE -> {
                RxHttp.deleteForm(url)
                    .addAll(params.toMap())
            }
            Method.PUT -> {
                RxHttp.putForm(url)
                    .addAll(params.toMap())
            }
        }

        http.toFlowResponse<T>()
            .onStart { onStart() }
            .catch {
                val msg = it.msg
                val code = it.code
                onError(code, msg)
            }
            .collect {
                onSuccess(it)
            }
    }

    /**
     * 使用RxJava 访问请求
     * 示例:
     * fun request(){
     *      requestRxJava<Any>("url", *arrayOf())
     *          .life(this)
     *          .doOnDispose {
     *              // 请求开始  显示加载中
     *          }
     *          .subscribe({
     *              // 请求成功
     *          },{
     *              val msg = it.msg
     *               val code = it.code
     *              // 请求失败
     *       })
     *   }
     * @param url String
     * @param params Array<out Pair<String, Any?>>
     * @param method Method
     * @return Observable<T>
     */
    inline fun<reified T: Any> requestRxJava(
        url: String,
        vararg params: Pair<String, Any?>,
        method: Method = Method.POST,
    ): Observable<T>{
        val http = when(method){
            Method.POST -> {
                RxHttp.postForm(url)
                    .addAll(params.toMap())
            }
            Method.GET -> {
                RxHttp.get(url)
                    .addAllQuery(params.toMap())
            }
            Method.DELETE -> {
                RxHttp.deleteForm(url)
                    .addAll(params.toMap())
            }
            Method.PUT -> {
                RxHttp.putForm(url)
                    .addAll(params.toMap())
            }
        }

        return http.asResponse<T>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 使用RxJava 访问请求
     * 示例:
     * fun request(){
     *      requestRxJavaList<Any>("url", *arrayOf())
     *          .doOnDispose {
     *              // 请求开始  显示加载中
     *          }
     *          .subscribe({
     *              // 请求成功
     *          },{
     *              val msg = it.msg
     *               val code = it.code
     *              // 请求失败
     *       })
     *   }
     * @param url String
     * @param params Array<out Pair<String, Any?>>
     * @param method Method
     * @return Observable<T>
     */
    inline fun<reified T: Any> requestRxJavaList(
        url: String,
        vararg params: Pair<String, Any?>,
        method: Method = Method.POST,
    ): Observable<MutableList<T>>{
        val http = when(method){
            Method.POST -> {
                RxHttp.postForm(url)
                    .addAll(params.toMap())
            }
            Method.GET -> {
                RxHttp.get(url)
                    .addAllQuery(params.toMap())
            }
            Method.DELETE -> {
                RxHttp.deleteForm(url)
                    .addAll(params.toMap())
            }
            Method.PUT -> {
                RxHttp.putForm(url)
                    .addAll(params.toMap())
            }
        }

        return http.asResponseList(T::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 下载
     * @param url String    下载原路径
     * @param localPath String  下载保存路径
     * @param onStart Function0<Unit>
     * @param onSuccess
     *          path: 返回本地存储路径, 不为空表示下载完成
     *          process: 已下载进度  0-100
     *          currentSize: 已下载size，单位：byte
     *          totalSize: 要下载的总size  单位：byte
     * @param onError
     */
    suspend fun download(
        url: String,
        localPath: String,
        onStart: () -> Unit,
        onSuccess: (path: String?, process: Int, currentSize: Long, totalSize: Long) -> Unit,
        onError: (code: String, msg: String) -> Unit
    ){
        RxHttp.get(url)
            .toFlowProgress(localPath)
            .onStart {
                onStart()
            }
            .catch {
                val msg = it.msg
                val code = it.code
                onError(code, msg)
            }
            .collect {
                //此时这里将收到所有事件，这里的it为ProgressT<String>对象
                val process = it.progress         //已下载进度  0-100
                val currentSize = it.currentSize  //已下载size，单位：byte
                val totalSize = it.totalSize      //要下载的总size  单位：byte
                val path = it.result              //本地存储路径
                onSuccess(path, process, currentSize, totalSize)
            }
    }
}