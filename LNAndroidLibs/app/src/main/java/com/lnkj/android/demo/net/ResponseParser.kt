package com.lnkj.android.demo.net

import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

@Parser(name = "Response")
open class ResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val data: Response<T> = response.convertTo(Response::class, *types)
        val t = data.data     //获取data字段
        if(data.code == 200 && types[0] == String::class.java){
            return "" as T
        }
        if (data.code != 200 || t == null) { //code不等于200，说明数据不正确，抛出异常
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t  //最后返回data字段
    }
}
