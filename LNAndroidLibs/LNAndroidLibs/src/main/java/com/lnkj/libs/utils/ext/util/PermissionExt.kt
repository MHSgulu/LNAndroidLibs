package com.lnkj.libs.utils.ext.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.permissionx.guolindev.PermissionX

fun AppCompatActivity.permissions(
    vararg permission: String,
    result: (allGranted: Boolean) -> Unit
) {
    PermissionX.init(this)
        .permissions(*permission)
        .onExplainRequestReason { scope, deniedList ->
            val message = "应用需要以下权限才能继续"
            scope.showRequestReasonDialog(deniedList, message, "同意", "拒绝")
        }
        .onForwardToSettings { scope, deniedList ->
            val message = "请在设置中允许以下权限"
            scope.showForwardToSettingsDialog(deniedList, message, "同意", "拒绝")
        }
        .request { allGranted, grantedList, deniedList ->
            result(allGranted)
        }
}

fun Fragment.permissions(
    vararg permission: String,
    result: (allGranted: Boolean) -> Unit
) {
    PermissionX.init(this)
        .permissions(*permission)
        .onExplainRequestReason { scope, deniedList ->
            val message = "应用需要以下权限才能继续"
            scope.showRequestReasonDialog(deniedList, message, "同意", "拒绝")
        }
        .onForwardToSettings { scope, deniedList ->
            val message = "请在设置中允许以下权限"
            scope.showForwardToSettingsDialog(deniedList, message, "同意", "拒绝")
        }
        .request { allGranted, grantedList, deniedList ->
            result(allGranted)
        }
}
