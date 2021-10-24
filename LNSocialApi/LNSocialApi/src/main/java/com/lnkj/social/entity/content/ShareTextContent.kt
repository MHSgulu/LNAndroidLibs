package com.lnkj.social.entity.content

/**
 * 文字分享
 */
data class ShareTextContent(
    override var description: String? = null,   //描述
    override var url: String? = null,   // 连接
    var atUser: String? = null
) : ShareContent()