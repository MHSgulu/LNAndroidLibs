package com.lnkj.android.demo.login

import android.os.Bundle
import com.lnkj.android.demo.databinding.ActivityLoginBinding
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.click
import com.lnkj.libs.core.dismissLoading
import com.lnkj.libs.core.showLoading
import com.lnkj.libs.state.UiState
import com.lnkj.libs.utils.toast

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        binding.btnGetCode.click {
            val phone = binding.etPhone.text.toString().trim()
            vm.getCode(phone)
        }

    }

    override fun createObserver() {
        vm.codeState.observeInActivity(this) {
            when (it) {
                is UiState.Start -> showLoading()
                is UiState.Error -> {
                    dismissLoading()
                    toast(it.msg)
                }
                is UiState.Next -> {
                    dismissLoading()
                    it.t
                }
            }
        }
    }
}