package com.lnkj.android.demo.ui.page.gallery

import android.Manifest
import android.os.Bundle
import com.blankj.utilcode.util.PermissionUtils
import com.lnkj.android.demo.databinding.ActivityGallerySaveBinding
import com.lnkj.android.demo.ui.dialog.DownloadDialog
import com.lnkj.gallerysaver.GalleryUtils
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.core.clickWithTrigger
import com.lnkj.libs.core.gone
import com.lnkj.libs.core.visible
import com.lnkj.libs.utils.toast
import com.lxj.xpopup.XPopup

/**
 *  
 */
class GallerySaveActivity : BaseActivity<GallerySaveViewModel, ActivityGallerySaveBinding>() {

	private var downloadDialog: DownloadDialog? = null

	private var canDownload = true

	override fun initView(savedInstanceState: Bundle?) {
		binding.appBar.ivBack.clickWithTrigger { finish() }
		binding.appBar.tvTitle.text = "保存图片视频到相册"

		binding.btnSaveImage.clickWithTrigger {
//			downloadDialog = DownloadDialog(this)
//			XPopup.Builder(this)
//				.dismissOnBackPressed(false)
//				.dismissOnTouchOutside(false)
//				.asCustom(downloadDialog)
//				.show()
			vm.downImage()
		}

		binding.btnSaveVideo.clickWithTrigger {
			vm.downVideo()
		}
	}

	override fun initData() {

		PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.callback(object : PermissionUtils.SimpleCallback{
				override fun onGranted() {

				}

				override fun onDenied() {

				}
			})
			.request()

	}

	override fun startObserve() {

		vm.progressLiveData.observe(this){
			print(it)
//			binding.smartProgressBar.setProgress(it.toFloat())
//			if(it == 100){
//				binding.smartProgressBar.gone()
//			}
		}

		vm.errorLiveData.observe(this){
			toast("图片下载失败")
//			binding.smartProgressBar.gone()
		}

		vm.downloadPathLiveData.observe(this){
//			binding.smartProgressBar.gone()
			GalleryUtils.saveImageToGalleryWithUrl(this, it){
				toast("图片保存成功")
			}
		}

		vm.progressVideoLiveData.observe(this){
//			binding.smartProgressBar.setProgress(it.toFloat())
//			if(it == 100){
//				binding.smartProgressBar.gone()
//			}
		}

		vm.errorVideoLiveData.observe(this){
			toast("视频下载失败")
//			binding.smartProgressBar.gone()
		}

		vm.downloadVideoPathLiveData.observe(this){
//			binding.smartProgressBar.gone()
			GalleryUtils.saveVideoToGallery(this, it){
				toast("视频保存成功")
			}
		}

	}

}