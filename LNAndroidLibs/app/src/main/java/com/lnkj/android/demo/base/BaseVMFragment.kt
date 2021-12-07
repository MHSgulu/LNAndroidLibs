package com.lnkj.android.demo.base

import androidx.viewbinding.ViewBinding
import com.lnkj.libs.base.BaseFragment
import com.lnkj.libs.base.BaseViewModel

abstract class BaseVMFragment<U: BaseViewModel, T: ViewBinding>: BaseFragment<U,T>()