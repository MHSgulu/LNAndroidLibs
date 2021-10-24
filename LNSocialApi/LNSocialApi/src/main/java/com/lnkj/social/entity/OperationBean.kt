package com.lnkj.social.entity

import android.content.Context
import com.lnkj.social.callback.OperationCallback
import com.lnkj.social.config.OperationType
import com.lnkj.social.config.PlatformType
import com.lnkj.social.entity.content.OperationContent

data class OperationBean(
    var operationContext: Context,        // 操作上下文
    var operationPlat: PlatformType,      // 平台类型
    var operationType: OperationType,     // 操作类型
    var operationCallback: OperationCallback,   // 回调
    var operationContent: OperationContent? = null  // 平台内容
)