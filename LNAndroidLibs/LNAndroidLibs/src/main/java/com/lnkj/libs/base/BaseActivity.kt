package com.lnkj.libs.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.itxca.msa.IMsa
import com.itxca.msa.msa
import com.lnkj.libs.R
import com.lnkj.libs.core.getVmClazz
import com.lnkj.libs.core.inflateBindingWithGeneric
import com.lnkj.libs.manager.NetState
import com.lnkj.libs.manager.NetworkStateManager
import com.lnkj.libs.utils.ext.util.hideSoftInput
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView


abstract class BaseActivity<VM: BaseViewModel, VB : ViewBinding>: AppCompatActivity(),IMsa by msa()  {

    protected lateinit var vm: VM
        private set

    lateinit var binding: VB

    private var loadingDialog: LoadingPopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initManageStartActivity()
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        vm = createViewModel()
        doCreateView(savedInstanceState)
        NetworkStateManager.instance.mNetworkStateCallback.observeInActivity(this) {
            onNetworkStateChanged(it)
        }
    }

    private fun doCreateView(savedInstanceState: Bundle?) {
        initImmersionBar()
        initIntent()
        initView(savedInstanceState)
        startObserve()
        initData()
    }

    open fun initImmersionBar() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.c_fb)
            titleBarMarginTop(binding.root)
        }
    }

    open fun initIntent() {}
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    open fun startObserve(){}

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

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

    fun showLoading(msg: String = "加载中..."){
        if(loadingDialog == null){
            loadingDialog = XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asLoading(msg)
        }
        if(loadingDialog?.isShow == true){
            loadingDialog?.dismiss()
        }
        loadingDialog?.show()
    }

    fun dismissLoading(){
        loadingDialog?.dismiss()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideSoftInput()
        return super.onTouchEvent(event)
    }

    //1.触摸事件接口
    interface MyOnTouchListener {
        fun onTouch(ev: MotionEvent?): Boolean
    }

    //2. 保存MyOnTouchListener接口的列表
    private val onTouchListeners = ArrayList<MyOnTouchListener>()

    //3.分发触摸事件给所有注册了MyOnTouchListener的接口
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        for (listener in onTouchListeners) {
            listener.onTouch(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    //4.提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
    open fun registerMyOnTouchListener(myOnTouchListener: MyOnTouchListener) {
        onTouchListeners.add(myOnTouchListener)
    }

    //5.提供给Fragment通过getActivity()方法来注销自己的触摸事件的方法
    open fun unregisterMyOnTouchListener(myOnTouchListener: MyOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener)
    }
}