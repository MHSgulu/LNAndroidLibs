package com.lnkj.android.demo.ui.dialog

import android.content.Context
import com.lnkj.android.demo.R
import com.lnkj.android.demo.databinding.DialogDownloadBinding
import com.lxj.xpopup.core.CenterPopupView

class DownloadDialog(context: Context): CenterPopupView(context) {

    private lateinit var binding: DialogDownloadBinding

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_download
    }

    override fun onCreate() {
        super.onCreate()
        binding = DialogDownloadBinding.bind(contentView)
    }

    fun setProgress(progress: Float){
        binding.smartProgressBar.setProgress(progress)
        if(progress == 100f){
            dismiss()
        }
    }

}