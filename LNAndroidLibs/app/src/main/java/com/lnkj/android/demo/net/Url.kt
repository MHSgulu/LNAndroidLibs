package com.lnkj.android.demo.net

import rxhttp.wrapper.annotation.DefaultDomain

object Url {

    // 默认域名
    @JvmField
    @DefaultDomain()
    var baseUrl = "http://192.168.0.104:80/"

    /**
     * 随机网络图片地址
     * @param width Int
     * @param height Int
     * @param key String
     * @return String
     */
    fun randomImageUrl(width: Int = 100, height: Int = 100, key: String = "LoveBusiness"): String{
        return "http://placeimg.com/$width/$height/${key.hashCode().toString() + key.toString()}"
    }



}