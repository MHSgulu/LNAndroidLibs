package com.lnkj.android.demo.ui.page.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.rxLifeScope
import com.lnkj.libs.base.BaseViewModel
import com.lnkj.libs.core.downloadFile
import com.lnkj.libs.utils.DirManager

class GallerySaveViewModel : BaseViewModel() {
    private var imageUrl = "https://up.enterdesk.com/edpic_source/53/0a/da/530adad966630fce548cd408237ff200.jpg"
    private var videoUrl = "http://vjs.zencdn.net/v/oceans.mp4"

    val progressLiveData = MutableLiveData<Int>()
    val errorLiveData = MutableLiveData<Boolean>()
    val downloadPathLiveData = MutableLiveData<String>()

    val progressVideoLiveData = MutableLiveData<Int>()
    val errorVideoLiveData = MutableLiveData<Boolean>()
    val downloadVideoPathLiveData = MutableLiveData<String>()

    fun downImage(){
        rxLifeScope.launch {
            downloadFile(imageUrl, "${DirManager.downloadDir}/${System.currentTimeMillis()}.png",{
                progressLiveData.value = it.progress
            },{
              errorLiveData.value = true
            },{
                downloadPathLiveData.value = it
            })
        }
    }

    fun downVideo(){
        rxLifeScope.launch {
            downloadFile(videoUrl, "${DirManager.downloadDir}/${System.currentTimeMillis()}.mp4",{
                progressVideoLiveData.value = it.progress
            },{
                errorVideoLiveData.value = true
            },{
                downloadVideoPathLiveData.value = it
            })
        }
    }
}