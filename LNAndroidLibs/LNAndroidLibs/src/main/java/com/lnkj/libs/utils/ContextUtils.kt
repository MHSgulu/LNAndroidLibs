package com.lnkj.libs.utils

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import com.blankj.utilcode.util.Utils

private class GlobalContext(context: Context) : ContextWrapper(context)

private lateinit var sApplication: Application
private lateinit var sGlobalContext: GlobalContext

fun initContext(application: Application) {
    if (!::sApplication.isInitialized) {
        sApplication = application
    }
    if (!::sGlobalContext.isInitialized) {
        sGlobalContext =
            GlobalContext(application.applicationContext)
    }
    Utils.init(application)
}

val context: Context
    get() = sGlobalContext


val application: Application
    get() = sApplication

/* Toast 相关 */

private val globalToast by lazy {
    Toast.makeText(context, null, Toast.LENGTH_SHORT)
}

/**
 * 及时的、尽快的显示一个Toast，多次调用此方法的Toast会被后调用的覆盖
 * @param text String 要显示的文本
 * @return Toast 单例的Toast对象
 */
fun toast(text: String): Toast {
    globalToast.duration = Toast.LENGTH_SHORT
    globalToast.setText(text)
    globalToast.show()
    return globalToast
}