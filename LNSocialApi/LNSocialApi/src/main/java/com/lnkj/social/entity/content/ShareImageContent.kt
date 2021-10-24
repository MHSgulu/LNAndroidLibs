package com.lnkj.social.entity.content

import android.graphics.Bitmap

/**
 * 图片分享实体类
 */
data class ShareImageContent(override var img: Bitmap?) : ShareContent()