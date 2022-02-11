package com.lnkj.android.demo.config

import com.lnkj.libs.config.HttpConfig

class HttpConfigImpl: HttpConfig {

    // 网络请求的baseUrl地址
    override val baseUrl: String
        get() = ""

    // 网络请求返回的code
    override val code: String
        get() = "code"
    // 网络请求返回的data
    override val data: String
        get() = "data"
    //网络请求返回的msg
    override val msg: String
        get() = "msg"
    // 默认的错误code
    override val errorCode: Int
        get() = 0
    // 默认成功code
    override val successCode: Int
        get() = 1

    override fun handlerNetworkError(code: Int) {
        // 根据特殊的错误code, 做对应的动作, 例如token过期, 进行登录操作
    }

    // 传递公共请求参数
    override fun httpCommonParams(): Map<String, Any> {
        return emptyMap()
    }

    // 传递公共请求头
    override fun httpRequestHeader(): Map<String, String> {
        return emptyMap()
    }
}