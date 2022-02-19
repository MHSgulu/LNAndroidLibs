package com.lnkj.android.demo.bean

import android.app.Activity
import com.lnkj.android.demo.ui.page.gallery.GallerySaveActivity

/**
 *
 */
data class ModuleBean(var title: String, var content: String, var clzz: Class<out Activity>)

// 案例功能
val mainDataList = arrayListOf(
    // 保存图片视频到本地
    ModuleBean(
        title = "保存图片视频到本地",
        content = "保存图片视频到本地相册， 兼容Android10+ 以及鸿蒙系统",
        clzz = GallerySaveActivity::class.java
    ),

    // 电子书
//    ModuleBean(
//        title = "电子书",
//        content = "epub电子书",
//        clzz = BookListActivity::class.java
//    )
)