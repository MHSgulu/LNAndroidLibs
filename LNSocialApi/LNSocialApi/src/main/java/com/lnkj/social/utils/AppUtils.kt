package com.lnkj.social.utils

import android.content.Context
import android.content.pm.PackageManager


object AppUtils {

    /**
     * 判断应用是否安装
     */
    fun isAppInstalled(name: String, context: Context): Boolean {
        var isInstalled: Boolean
        val manager = context.packageManager
        try {
            val applicationInfo = manager.getApplicationInfo(name, 0) ?: null
            isInstalled = applicationInfo != null
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            isInstalled = false
        }
        return isInstalled
    }
}