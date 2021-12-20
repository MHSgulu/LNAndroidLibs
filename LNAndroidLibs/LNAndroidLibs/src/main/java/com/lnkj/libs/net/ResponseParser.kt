package com.lnkj.libs.net

import com.alibaba.fastjson.JSON
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.utils.ext.util.GsonUtils
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import java.io.IOException
import java.lang.reflect.Type

@Parser(name = "Response")
open class ResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val json = response.body?.string()
        val jsonObject = JSON.parseObject(json)
        val code = jsonObject.getIntValue(MVVMLibs.httpConfig?.code)
        val msg = jsonObject.getString(MVVMLibs.httpConfig?.msg)

        if (code != MVVMLibs.httpConfig?.successCode) {
            MVVMLibs.httpConfig?.handlerNetworkError(code)
            throw ParseException(code.toString(), msg, response)
        }

        if (code == MVVMLibs.httpConfig?.successCode && types[0] === Any::class.java) {
            return Any() as T
        }

        return GsonUtils.INSTANCE.fromJson(
            jsonObject.getString(MVVMLibs.httpConfig?.data),
            types[0]
        )  //最后返回data字段
    }
}
