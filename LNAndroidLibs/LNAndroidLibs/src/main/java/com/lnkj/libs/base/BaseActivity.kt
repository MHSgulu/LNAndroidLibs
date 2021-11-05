package com.lnkj.libs.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.itxca.msa.IMsa
import com.itxca.msa.msa
import com.lnkj.libs.net.AppException
import com.lnkj.libs.utils.ext.getVmClazz
import com.lnkj.libs.utils.ext.inflateBindingWithGeneric
import com.lnkj.libs.utils.ext.view.dismissLoading
import com.lnkj.libs.utils.ext.view.showLoading
import com.lnkj.libs.utils.toast

abstract class BaseActivity<VM: BaseViewModel, VB : ViewBinding>: AppCompatActivity(),IMsa by msa()  {

    protected lateinit var vm: VM
        private set

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initManageStartActivity()
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        vm = createViewModel()
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
    }

    abstract fun initView(savedInstanceState: Bundle?)


    abstract fun createObserver()

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }


    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        vm.uiState.showDialog.observeInActivity(this) {
            showLoading(it)
        }
        //关闭弹窗
        vm.uiState.dismissDialog.observeInActivity(this) {
            dismissLoading()
        }
        // 错误处理
        vm.uiState.error.observeInActivity(this){
            onError(it)
        }
    }

    protected fun onError(error: AppException){
        toast(error.errorMsg)
    }
}