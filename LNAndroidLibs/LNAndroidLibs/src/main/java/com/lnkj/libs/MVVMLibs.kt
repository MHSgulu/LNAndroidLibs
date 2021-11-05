package com.lnkj.libs

import android.app.Application
import com.lnkj.libs.utils.initContext
import kotlin.properties.Delegates

object MVVMLibs {

    fun init(application: Application){
        initContext(application)
    }

}