package com.lnkj.libs.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.itxca.msa.IMsa
import com.itxca.msa.msa
import com.lnkj.libs.core.getVmClazz
import com.lnkj.libs.core.inflateBindingWithGeneric


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
        initView(savedInstanceState)
        createObserver()
    }

    abstract fun initView(savedInstanceState: Bundle?)


    abstract fun createObserver()

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }


    override fun onPause() {
        super.onPause()
        KeyboardUtils.hideSoftInput(this)
    }

    private var lastBackPressTime = 0L
    /**
     * 两次返回退出Activity
     */
    protected fun doubleBackToFinish(duration: Long = 2000, toast: String = "再按一次退出") {
        if (!FragmentUtils.dispatchBackPress(supportFragmentManager)) {
            if(System.currentTimeMillis() - lastBackPressTime < duration){
                ToastUtils.cancel()
                super.onBackPressed()
            }else{
                lastBackPressTime = System.currentTimeMillis()
                ToastUtils.showShort(toast)
            }
        }
    }
}