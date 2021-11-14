package com.lnkj.android.demo.login

import android.os.Bundle
import com.lnkj.android.demo.databinding.ActivityLoginBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.click
import com.lnkj.libs.state.observeState
import com.lnkj.libs.utils.toast

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        binding.btnGetCode.click {
            val phone = binding.etPhone.text.toString().trim()
            vm.getCode(phone)
        }

    }

    override fun initData() {

    }

    override fun startObserve() {

        vm.codeState.observeState(this){
            onLading = {
                showLoading()
            }
            onError = {code, msg ->
                dismissLoading()
                toast(msg)
            }
            onSuccess = {
                dismissLoading()
                it
            }
        }
    }
}