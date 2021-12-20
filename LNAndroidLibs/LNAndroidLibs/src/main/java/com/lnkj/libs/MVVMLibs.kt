package com.lnkj.libs

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.lnkj.libs.config.HttpConfig
import com.lnkj.libs.config.HttpConfigImpl
import com.lnkj.libs.net.RxHttpManager
import com.lnkj.libs.utils.DirManager
import com.lnkj.libs.utils.HttpsUtils
import com.lnkj.libs.utils.initContext
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
object MVVMLibs {

    var isDebug = true
    var defaultLogTag = "LNAndroidLibs"
    var sharedPrefName = "LNAndroidLibs"
    var handler = Handler(Looper.getMainLooper())

    var httpConfig: HttpConfig? = null

    fun init(
        application: Application, isDebug: Boolean = true,
        defaultLogTag: String = MVVMLibs.defaultLogTag,
        sharedPrefName: String = MVVMLibs.sharedPrefName,
        httpConfig: HttpConfig = HttpConfigImpl()
    ) {
        this.isDebug = isDebug
        this.defaultLogTag = defaultLogTag
        this.sharedPrefName = sharedPrefName
        this.httpConfig = httpConfig
        initContext(application)
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.getDefaultMaker().setBgResource(R.drawable._ktx_toast_bg)
        ToastUtils.getDefaultMaker().setTextColor(Color.WHITE)
        initRefresh()
        DirManager.init()
        MMKV.initialize(application)
        RxHttpManager.init(application)
    }

    private fun initRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }
    }

}