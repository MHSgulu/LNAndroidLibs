package com.lnkj.android.demo

import android.app.Application
import com.lnkj.android.demo.config.HttpConfigImpl
import com.lnkj.libs.MVVMLibs
import com.lnkj.libs.net.RxHttpManager

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        MVVMLibs.init(this, httpConfig = HttpConfigImpl())
        // 网络初始化
        RxHttpManager.init(this)
    }

}