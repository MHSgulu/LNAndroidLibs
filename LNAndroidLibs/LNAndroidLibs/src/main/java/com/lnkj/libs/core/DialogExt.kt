package com.lnkj.libs.core

import android.app.Activity
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

class DialogExt {
    companion object{
        var loadingPopupView: LoadingPopupView? = null
    }
}

fun Activity.showLoading(msg: String = "加载中..."){
    DialogExt.loadingPopupView = XPopup.Builder(this)
        .dismissOnTouchOutside(false)
        .asLoading(msg)
    DialogExt.loadingPopupView?.show()
}

fun Activity.dismissLoading(){
    DialogExt.loadingPopupView?.dismiss()
}

fun Fragment.showLoading(msg: String = "加载中..."){
    DialogExt.loadingPopupView = XPopup.Builder(context)
        .dismissOnTouchOutside(false)
        .asLoading(msg)
    DialogExt.loadingPopupView?.show()
}

fun Fragment.dismissLoading(){
    DialogExt.loadingPopupView?.dismiss()
}