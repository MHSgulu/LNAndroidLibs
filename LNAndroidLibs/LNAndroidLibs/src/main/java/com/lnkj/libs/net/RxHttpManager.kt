package com.lnkj.libs.net

import android.app.Application
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.net.interceptor.CacheInterceptor
import com.lnkj.libs.net.interceptor.logging.LogInterceptor
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.Param
import java.io.File
import java.util.concurrent.TimeUnit

class RxHttpManager private constructor() {

    companion object {
        fun init(context: Application) {
            val build = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }

            if (MVVMLibs.isDebug) {
                build.addInterceptor(CacheInterceptor())
                    .addInterceptor(LogInterceptor())
            }
            val client = build.build()
            val cacheFile = File(context.externalCacheDir, "RxHttpCache")
            val rxhttp = RxHttpPlugins.init(client)
                .setDebug(false, true)
            if (MVVMLibs.isDebug) {
                rxhttp.setCache(cacheFile, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                    .setExcludeCacheKeys("time") //设置一些key，不参与cacheKey的组拼
            }
            rxhttp.setOnParamAssembly { p: Param<*> ->
                p.addAllHeader(MVVMLibs.httpConfig?.httpRequestHeader() ?: emptyMap())
                p.addAll(MVVMLibs.httpConfig?.httpCommonParams())
            }
        }
    }
}