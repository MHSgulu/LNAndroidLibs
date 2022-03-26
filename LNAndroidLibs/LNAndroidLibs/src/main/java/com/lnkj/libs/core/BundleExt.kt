package com.lnkj.libs.core

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun Bundle.putExtras(vararg params: Pair<String, Any?>): Bundle {
    if (params.isEmpty()) return this
    params.forEach { (key, value) ->
        when (value) {
            is Int -> putInt(key, value)
            is Byte -> putByte(key, value)
            is Char -> putChar(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Short -> putShort(key, value)
            is Double -> putDouble(key, value)
            is Boolean -> putBoolean(key, value)
            is Bundle -> putAll(value)
            is String -> putString(key, value)
            is IntArray -> putIntArray(key, value)
            is ByteArray -> putByteArray(key, value)
            is CharArray -> putCharArray(key, value)
            is LongArray -> putLongArray(key, value)
            is FloatArray -> putFloatArray(key, value)
            is Parcelable -> putParcelable(key, value)
            is ShortArray -> putShortArray(key, value)
            is DoubleArray -> putDoubleArray(key, value)
            is BooleanArray -> putBooleanArray(key, value)
            is CharSequence -> putCharSequence(key, value)
            is Array<*> -> {
                when {
                    value.isArrayOf<String>() ->
                        putStringArray(key, value as Array<String?>)
                    value.isArrayOf<Parcelable>() ->
                        putParcelableArray(key, value as Array<Parcelable?>)
                    value.isArrayOf<CharSequence>() ->
                        putCharSequenceArray(key, value as Array<CharSequence?>)
                }
            }
            is Serializable -> putSerializable(key, value)
        }
    }
    return this
}

fun main() {
    Bundle().putExtras()
}