package com.lnkj.social.handler.ali

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import com.alipay.sdk.app.AuthTask
import com.alipay.sdk.app.PayTask
import com.lnkj.social.PlatformManager
import com.lnkj.social.callback.AuthCallback
import com.lnkj.social.callback.OperationCallback
import com.lnkj.social.callback.PayCallback
import com.lnkj.social.config.OperationType
import com.lnkj.social.config.PlatformType
import com.lnkj.social.config.SocialConstants
import com.lnkj.social.entity.content.AliAuthContent
import com.lnkj.social.entity.content.AliPayContent
import com.lnkj.social.entity.content.AuthContent
import com.lnkj.social.entity.content.PayContent
import com.lnkj.social.entity.platform.AliPlatConfigBean
import com.lnkj.social.entity.platform.PlatformConfig
import com.lnkj.social.handler.SSOHandler

class AliHandler(context: Context, config: PlatformConfig) : SSOHandler() {

    companion object {
        const val TAG = "AliHandler"

        private val opList: List<OperationType> = listOf(
            OperationType.AUTH,
            OperationType.PAY
        )

        private const val SDK_PAY_FLAG = 11
        private const val SDK_AUTH_FLAG = 2
        /**
         * 支付宝支付成功
         */
        const val ALI_9000 = "9000"
        /**
         * 支付宝支付失败
         */
        const val ALI_4000 = "4000"
        /**
         * 支付宝支付取消
         */
        const val ALI_6001 = "6001"

        const val STRING_ONE = "0"
    }

    private var mContext = context
    private lateinit var mAuthCallback: AuthCallback
    private lateinit var mPayCallback: PayCallback
    private var mAuthToken: String? = null
    private var mHandler: Handler? = null

    init {
        if (config is AliPlatConfigBean) {
            mAuthToken = config.authToken
        }
    }

    override val isInstalled: Boolean
        get() = true

    override fun pay(type: PlatformType, content: PayContent, callback: OperationCallback) {
        setPlatCurrentHandler(callback)
        if (mContext !is Activity) {
            callback.onErrors?.invoke(
                type, SocialConstants.CONTEXT_ERROR,
                "$TAG : context 类型错误"
            )
            return
        }
        if (content !is AliPayContent) {
            callback.onErrors?.invoke(
                type, SocialConstants.CONTEXT_ERROR,
                "$TAG : content 类型错误"
            )
            return
        }
        val payInfo = content.orderInfo
        val activity = mContext as Activity

        initHandler()
        Thread {
            // 构造AuthTask 对象
            // 调用授权接口，获取授权结果
            val result = PayTask(activity)
                .payV2(payInfo.replace("&amp", "&"), true)
            val msg = Message()
            msg.arg1 = SDK_PAY_FLAG
            msg.obj = result
            if (mHandler != null) {
                mHandler!!.sendMessage(msg)
            }
        }.start()
    }

    override fun authorize(type: PlatformType, callback: OperationCallback, content: AuthContent?) {
        setPlatCurrentHandler(callback)
        if (mContext !is Activity) {
            callback.onErrors?.invoke(
                type, SocialConstants.CONTEXT_ERROR,
                "$TAG : context 类型错误"
            )
            return
        }
        if (content !is AliAuthContent) {
            callback.onErrors?.invoke(
                type, SocialConstants.CONTEXT_ERROR,
                "$TAG : content 类型错误"
            )
            return
        }
        val authInfo = content.authInfo
        val activity = mContext as Activity
        initHandler()
        // 必须异步调用
        Thread {
            // 构造AuthTask 对象
            // 调用授权接口，获取授权结果
            val result = AuthTask(activity).authV2(authInfo, true)
            val msg = Message()
            msg.arg1 = SDK_AUTH_FLAG
            msg.obj = result
            if (mHandler != null) {
                mHandler!!.sendMessage(msg)
            }
        }.start()
    }

    override fun release() {
        mHandler?.removeCallbacksAndMessages(null)
        mHandler = null
    }

    /**
     * 获取该平台支持的操作
     */
    fun getAvailableOperation(): List<OperationType> {
        return opList
    }

    private fun initHandler() {
        mHandler = object : Handler(mContext.mainLooper) {
            override fun handleMessage(msg: Message) {
                when (msg.arg1) {
                    SDK_AUTH_FLAG -> {
                        if (msg.obj == null) {
                            mAuthCallback.onErrors?.invoke(
                                PlatformType.ALI, SocialConstants.AUTH_ERROR,
                                "$TAG : 授权为空"
                            )
                            return
                        }
                        val resultStatus = (msg.obj as MutableMap<*, *>)["resultStatus"]
                        Log.e("AliAuth", resultStatus.toString())
                        when {
                            ALI_9000 == resultStatus -> {
                                val result = (msg.obj as MutableMap<*, *>)["result"]
                                val aliToken = getAliToken(result.toString())
                                val map = mutableMapOf<String, String?>()
                                aliToken?.let {
                                    map["auth_code"] = aliToken["auth_code"]
                                    map["auth_code"] = aliToken["auth_code"]
                                    map["user_id"] = aliToken["user_id"]
                                }
                                mAuthCallback.onSuccess?.invoke(PlatformType.ALI, map)
                            }
                            ALI_6001 == resultStatus -> mAuthCallback.onCancel?.invoke(PlatformType.ALI)
                            else -> mAuthCallback.onErrors?.invoke(
                                PlatformType.ALI,
                                SocialConstants.AUTH_ERROR, "$TAG : 授权失败"
                            )
                        }
                    }
                    SDK_PAY_FLAG -> {
                        val resultStatus = (msg.obj as Map<*, *>)["resultStatus"]
                        Log.e("AliPay", resultStatus.toString())
                        when {
                            ALI_9000 == resultStatus -> mPayCallback.onSuccess?.invoke(PlatformType.ALI)
                            ALI_6001 == resultStatus -> mPayCallback.onCancel?.invoke(PlatformType.ALI)
                            else -> mPayCallback.onErrors?.invoke(
                                PlatformType.ALI,
                                SocialConstants.PAY_ERROR, "$TAG : 支付失败"
                            )
                        }
                    }
                }
                release()
            }
        }
    }

    private fun getAliToken(str: String): MutableMap<String, String>? {
        if (str.isBlank()) return null
        val map = mutableMapOf<String, String>()
        val udderCode = str.split("&".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        for (s in udderCode) {
            var value: String
            val split = s.split("=".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            value = if (split.size > 1) split[1] else ""
            val key = split[0]
            when (key) {
                "auth_code" -> map["auth_code"] = value
                "user_id" -> map["user_id"] = value
                "success" -> map["success"] = value
                "result_code" -> map["result_code"] = value
            }
        }
        return map
    }

    /**
     * 设置PlatformManager.currentHandler,用于回调
     */
    private fun setPlatCurrentHandler(callback: OperationCallback) {
        if(callback is PayCallback) {
            mPayCallback = callback
        }
        if(callback is AuthCallback){
            mAuthCallback = callback
        }
//    PlatformManager.currentHandlerMap[this.hashCode()] = this
        PlatformManager.currentHandler = this
    }

}