package com.lnkj.libs.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.base.BaseFragment
import com.lnkj.libs.utils.context
import com.lnkj.libs.utils.livedata.LifecycleHandler

inline val FragmentActivity.lifecycleHandler
    get() = LifecycleHandler(this)

inline val Fragment.lifecycleHandler
    get() = LifecycleHandler(this)

/**
 * 将Activity移到前台，需将launchMode设置为SingleTop，否则会创建新实例
 */
fun Activity.moveTaskToFront() {
    val intent = Intent(context, this.javaClass)
    intent.flags =
        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP
    context.startActivity(intent)
}

fun addActivityLifecycleCallbacks(callbacks: Utils.ActivityLifecycleCallbacks){
    ActivityUtils.addActivityLifecycleCallbacks(callbacks)
}

fun removeActivityLifecycleCallbacks(callbacks: Utils.ActivityLifecycleCallbacks){
    ActivityUtils.removeActivityLifecycleCallbacks(callbacks)
}

fun Activity.addActivityLifecycleCallbacks(callbacks: Utils.ActivityLifecycleCallbacks){
    ActivityUtils.addActivityLifecycleCallbacks(this, callbacks)
}

fun Activity.removeActivityLifecycleCallbacks(callbacks: Utils.ActivityLifecycleCallbacks){
    ActivityUtils.removeActivityLifecycleCallbacks(this, callbacks)
}

