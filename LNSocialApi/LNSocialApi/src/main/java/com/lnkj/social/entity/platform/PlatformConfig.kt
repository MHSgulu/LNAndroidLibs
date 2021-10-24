package com.lnkj.social.entity.platform

import com.lnkj.social.config.PlatformType

interface PlatformConfig {
    val name: PlatformType     // 平台类型
    var appkey:String?          // 应用id
}