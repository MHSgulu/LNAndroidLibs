package com.lnkj.libs.state

import com.lnkj.libs.net.AppException
import com.lnkj.libs.utils.livedata.event.EventLiveData

class UiState {

    //显示加载框
    val showDialog by lazy { EventLiveData<String>() }
    //隐藏
    val dismissDialog by lazy { EventLiveData<Boolean>() }

    // 错误处理
    val error by lazy { EventLiveData<AppException>() }

}