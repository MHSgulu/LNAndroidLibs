package com.lnkj.android.demo.net

import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

@Parser(name = "OkResponse")
open class OkResponseParser : TypeParser<Any> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): Any {
        val data: Response<Any> = response.convertTo(Response::class, *types)
        if (data.code != 200) {
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return Any()  //最后返回data字段
    }
}
