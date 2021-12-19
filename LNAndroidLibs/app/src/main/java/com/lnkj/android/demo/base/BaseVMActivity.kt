package com.lnkj.android.demo.base

import android.view.MotionEvent
import androidx.viewbinding.ViewBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.utils.ext.util.hideSoftInput


abstract class BaseVMActivity<U: BaseViewModel, T: ViewBinding>: BaseActivity<U, T>() {


}