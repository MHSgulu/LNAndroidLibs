package com.lnkj.libs.core

import android.content.Context
import android.content.SharedPreferences
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.utils.context

/**
 * Description: SharedPreferences相关
 */

fun Any.sp(name: String = MVVMLibs.sharedPrefName) = context.getSharedPreferences(name, Context.MODE_PRIVATE)

/**
 * 批处理
 */
fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    edit().apply { action() }.apply()
}

/**
 * 对象操作
 */
fun SharedPreferences.putObject(key: String, obj: Any?) {
    putString(key, obj?.toJson()?:"")
}

inline fun <reified T> SharedPreferences.getObject(key: String): T? {
    val string = getString(key, null)
    if(string==null || string.isEmpty())return null
    return getString(key, null)?.toBean<T>()
}


/**
 *  put系列
 */
fun SharedPreferences.putString(key: String, value: String) {
    edit { putString(key, value) }
}

fun SharedPreferences.putInt(key: String, value: Int) {
    edit { putInt(key, value) }
}

fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    edit { putBoolean(key, value) }
}

fun SharedPreferences.putFloat(key: String, value: Float) {
    edit { putFloat(key, value) }
}

fun SharedPreferences.putLong(key: String, value: Long) {
    edit { putLong(key, value) }
}

fun SharedPreferences.putStringSet(key: String, value: Set<String>) {
    edit { putStringSet(key, value) }
}

fun SharedPreferences.clear(key: String) {
    edit { remove(key) }
}

fun SharedPreferences.clear() {
    edit { clear() }
}
