package com.lnkj.android.demo

import android.app.Application
import com.lnkj.android.demo.net.RxHttpManager
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.utils.application

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MVVMLibs.init(this)
        RxHttpManager.init(application)
    }

}