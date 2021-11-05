package com.lnkj.libs.utils.livedata

import androidx.lifecycle.MutableLiveData


class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}