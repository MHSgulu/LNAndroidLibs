package com.lnkj.android.demo.net

import com.google.gson.annotations.SerializedName


class Response<T> {
    @SerializedName("code")
    var code = 0
    @SerializedName("msg")
    var msg: String = ""
    var data: T? = null
}
