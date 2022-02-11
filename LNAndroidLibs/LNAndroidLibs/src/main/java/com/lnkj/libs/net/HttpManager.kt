package com.lnkj.libs.net

import android.app.Application
import com.blankj.utilcode.util.AppUtils
import com.lnkj.libs.net.interceptor.CacheInterceptor
import com.lnkj.libs.net.interceptor.logging.LogInterceptor
import com.rxjava.rxlife.life
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.Param
import rxhttp.wrapper.param.RxHttp
import java.io.File
import java.util.concurrent.TimeUnit

class HttpManager private constructor() {

    companion object {

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }
                .addInterceptor(CacheInterceptor())
                .addInterceptor(LogInterceptor())
                .build()
        }

        /**
         * 网络请求初始化
         * @param context Application
         * @param client OkHttpClient
         * @param headers Map<String, String>
         * @param params Array<out Pair<String, Any>>
         */
        fun init(
            context: Application,
            client: OkHttpClient = getOkHttpClient(),
            headers: Map<String, String> = emptyMap(),
            vararg params: Pair<String, Any>
        ) {
            val cacheFile = File(context.externalCacheDir, "LNAndroidLibsHttpCache")
            RxHttpPlugins.init(client)
                .setDebug(false, true)
                .setCache(cacheFile, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                .setExcludeCacheKeys("time") //设置一些key，不参与cacheKey的组拼
                .setOnParamAssembly { p: Param<*> ->
                    // 添加公共header
                    p.addHeader("deviceType", "android") //添加公共请求头
                    p.addHeader("version", AppUtils.getAppVersionName()) //添加公共请求头
                    p.addHeader("timestamp", System.currentTimeMillis().toString()) //添加公共请求头
                    p.addAllHeader(headers)
                    p.addAll(params.toMap())
                }
        }
    }
}