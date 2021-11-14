package com.lnkj.android.demo.login

import androidx.lifecycle.rxLifeScope
import com.lnkj.android.demo.utils.ext.okRequest
import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.state.StateData
import com.lnkj.libs.state.StatefulMutableLiveData
import com.lnkj.libs.utils.livedata.event.EventLiveData

class LoginViewModel : BaseViewModel() {

    val codeState = StatefulMutableLiveData<Any>()

    fun getCode(phone: String) {

        rxLifeScope.launch {
            okRequest(
                "Api/Public/get_sms_code",
                "mobile" to phone,
                "type" to 5,
                onStart = {
                    codeState.value = StateData.Loading
                },
                onSuccess = {
                    codeState.value = StateData.Success(Any())
                },
                onError = { msg, code ->
                    codeState.value = StateData.Error(msg, code)
                })
        }
    }


}

