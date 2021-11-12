package com.lnkj.android.demo.net

import com.google.gson.annotations.SerializedName


class Response<T> {
    @SerializedName("status")
    var code = 0
    @SerializedName("info")
    var msg: String = ""
    var data: T? = null
}
