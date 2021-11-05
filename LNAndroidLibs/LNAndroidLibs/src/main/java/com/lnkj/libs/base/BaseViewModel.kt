package com.lnkj.libs.base

import androidx.lifecycle.ViewModel
import com.lnkj.libs.state.UiState
import com.lnkj.libs.utils.livedata.event.EventLiveData

open class BaseViewModel : ViewModel(){

    val uiState: UiState by lazy { UiState() }

}