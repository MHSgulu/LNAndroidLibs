package com.lnkj.libs.utils.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.core.content.FileProvider
import java.io.File
import java.io.Serializable

/**
 * Return the Intent with [Settings.ACTION_APPLICATION_DETAILS_SETTINGS]
 */
fun Context.getAppInfoIntent(packageName: String = this.packageName): Intent =
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

/**
 * Return the Intent with [Settings.ACTION_DATE_SETTINGS]
 */
fun Context.getDateAndTimeIntent(): Intent =
    Intent(Settings.ACTION_DATE_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra("packageName", packageName)
    }

/**
 * Return the Intent with [Settings.ACTION_LOCALE_SETTINGS]
 */
fun Context.getLanguageIntent() =
    Intent(Settings.ACTION_LOCALE_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra("packageName", packageName)
    }

/**
 * Return the Intent for install apk
 */
fun Context.getInstallIntent(apkFile: File): Intent? {
    if (!apkFile.exists()) return null
    val intent = Intent(Intent.ACTION_VIEW)
    val uri: Uri

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        uri = Uri.fromFile(apkFile)
    } else {
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        val authority = "$packageName.fileprovider"
        uri = FileProvider.getUriForFile(this, authority, apkFile)
    }
    intent.setDataAndType(uri, "application/vnd.android.package-archive")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

/**
 * 获取String类型参数
 * @receiver Intent
 * @param key String
 * @return String
 */
fun Intent.getString(key: String, default: String = ""): String {
    return if (this.hasExtra(key)){
        getStringExtra(key)?:default
    } else {
        default
    }
}


fun Intent.fillIntentArguments(vararg params: Pair<String, Any?>): Intent {
    params.forEach {
        when (val value = it.second) {
            null -> putExtra(it.first, null as Serializable?)
            is Int -> putExtra(it.first, value)
            is Long -> putExtra(it.first, value)
            is CharSequence -> putExtra(it.first, value)
            is String -> putExtra(it.first, value)
            is Float -> putExtra(it.first, value)
            is Double -> putExtra(it.first, value)
            is Char -> putExtra(it.first, value)
            is Short -> putExtra(it.first, value)
            is Boolean -> putExtra(it.first, value)
            is Serializable -> putExtra(it.first, value)
            is Bundle -> putExtra(it.first, value)
            is Parcelable -> putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                value.isArrayOf<String>() -> putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> putExtra(it.first, value)
            is LongArray -> putExtra(it.first, value)
            is FloatArray -> putExtra(it.first, value)
            is DoubleArray -> putExtra(it.first, value)
            is CharArray -> putExtra(it.first, value)
            is ShortArray -> putExtra(it.first, value)
            is BooleanArray -> putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
    }
    return this
}


internal fun <T> Bundle.read(key: String, defaultValue: T): T {
    return when (defaultValue) {
        //基本类型
        is Int -> getInt(key, defaultValue) as T
        is Byte -> getByte(key, defaultValue) as T
        is Char -> getChar(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        is Short -> getShort(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Double -> getDouble(key, defaultValue) as T
        is String -> getString(key, defaultValue) as T
        //可序列化的类型
        is Bundle -> getBundle(key) as? T ?: defaultValue
        is CharSequence -> getCharSequence(key) as? T ?: defaultValue
        is Parcelable -> getParcelable<Parcelable>(key) as? T ?: defaultValue
        //基本类型的数组
        is IntArray -> getIntArray(key) as? T ?: defaultValue
        is ByteArray -> getByteArray(key) as? T ?: defaultValue
        is CharArray -> getCharArray(key) as? T ?: defaultValue
        is LongArray -> getLongArray(key) as? T ?: defaultValue
        is ShortArray -> getShortArray(key) as? T ?: defaultValue
        is FloatArray -> getFloatArray(key) as? T ?: defaultValue
        is DoubleArray -> getDoubleArray(key) as? T ?: defaultValue
        //其它类型的数组
        /*is Array<*> -> when {
            else ->
        }*/
        //列表
        /*is List<*> -> when {
            else ->
        }*/
        is Serializable -> getSerializable(key) as? T ?: defaultValue
        else -> throw IllegalArgumentException("暂时不支持代理的类型。")
    }
}

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

/**
 * Add the [Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

/**
 * Add the [Intent.FLAG_ACTIVITY_MULTIPLE_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }


/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_ANIMATION] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * Add the [Intent.FLAG_ACTIVITY_SINGLE_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }
