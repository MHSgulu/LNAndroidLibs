package com.lnkj.android.demo

import android.app.Application
import com.lnkj.libs.MVVMLibs

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MVVMLibs.init(this)
    }

}