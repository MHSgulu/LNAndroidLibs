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
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.base.BaseFragment
import com.lnkj.libs.utils.context
import com.lnkj.libs.utils.livedata.LifecycleHandler

/**
 * Description: Activity相关
 */

inline fun <reified T> BaseFragment<*, *>.startPage(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null
) {
    val intent = Intent(activity, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    startActivity(intent)
}

inline fun <reified T : BaseActivity<*, *>> BaseFragment<*, *>.startForResult(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null,
    requestCode: Int = -1,
    crossinline callBack: (code: Int, data: Intent?) -> Unit
) {

    T::class.startForResult(
        {
            if (flag != -1) {
                this.addFlags(flag)
            }
            if (bundle != null) putExtras(bundle.toBundle()!!)
        }
    ) { code, data ->
        callBack.invoke(code, data)
    }
}

inline fun <reified T> Context.startPage(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null
) {
    val intent = Intent(this, T::class.java).apply {
        if (flag != -1) {
            this.addFlags(flag)
        }
        if (this !is Activity) {
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (bundle != null) putExtras(bundle.toBundle()!!)
    }
    startActivity(intent)
}

inline fun <reified T> View.startPage(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null
) {
    context.startPage<T>(flag, bundle)
}

inline fun <reified T: BaseActivity<*,*>> View.startForResult(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null,
    requestCode: Int = -1,
    crossinline callBack: (code: Int, data: Intent?) -> Unit
) {
    (context as BaseActivity<*,*>).startForResult<T>(flag, bundle, requestCode, callBack)
}

inline fun <reified T: BaseActivity<*,*>> BaseActivity<*,*>.startForResult(
    flag: Int = -1,
    bundle: Array<out Pair<String, Any?>>? = null,
    requestCode: Int = -1,
    crossinline callBack: (code: Int, data: Intent?) -> Unit
) {
    T::class.startForResult(
        {
            if (flag != -1) {
                this.addFlags(flag)
            }
            if (bundle != null) putExtras(bundle.toBundle()!!)
        }
    ) { code, data ->
        callBack.invoke(code, data)
    }
}

fun FragmentActivity.finishDelay(delay: Long = 1) {
    LifecycleHandler(this).postDelayed({ finish() }, delay)
}

//post, postDelay
fun FragmentActivity.post(action: () -> Unit) {
    LifecycleHandler(this).post { action() }
}

fun FragmentActivity.postDelay(delay: Long = 0, action: () -> Unit) {
    LifecycleHandler(this).postDelayed({ action() }, delay)
}

//view model
fun <T : ViewModel> FragmentActivity.getVM(clazz: Class<T>) = ViewModelProvider(this).get(clazz)

/**
 * saved state view model，要求ViewModel的构造必须接受SavedStateHandle类型的参数，比如：
 * ```
 * class DemoVM( handler: SavedStateHandle): ViewModel()
 * ```
 */
fun <T : ViewModel> FragmentActivity.getSavedStateVM(clazz: Class<T>) = ViewModelProvider(
    this, SavedStateViewModelFactory(
        context as Application, this
    )
).get(clazz)


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