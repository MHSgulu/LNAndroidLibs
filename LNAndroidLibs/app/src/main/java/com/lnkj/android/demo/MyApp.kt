package com.lnkj.android.demo

import android.app.Application
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.net.HttpManager

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MVVMLibs.init(this)
        HttpManager.init(this)
    }

}