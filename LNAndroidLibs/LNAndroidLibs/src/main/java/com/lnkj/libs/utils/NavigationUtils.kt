package com.lnkj.libs.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lnkj.libs.base.BaseActivity
import com.lnkj.libs.base.BaseFragment
import com.lnkj.libs.utils.ext.*
import java.io.File


/**
 * Jump to the app info page
 */
fun Context.goToAppInfoPage(packageName: String = this.packageName) {
    startActivity(getAppInfoIntent(packageName))
}

/**
 * Jump to the data and time page
 */
fun Context.goToDateAndTimePage() {
    startActivity(getDateAndTimeIntent())
}

/**
 * Jump to the language page
 */
fun Context.goToLanguagePage() {
    startActivity(getLanguageIntent())
}

/**
 * Jump to the accessibility page
 */
fun Context.goToAccessibilitySetting() =
    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).run { startActivity(this) }

/**
 * install apk
 * @note need android.permission.REQUEST_INSTALL_PACKAGES after N
 */
fun Context.installApk(apkFile: File) {
    val intent = getInstallIntent(apkFile)
    intent?.run { startActivity(this) }
}

/**
 * Visit the specific url with browser
 */
fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
}

/**
 * Visit app in app store
 * @param packageName default value is current app
 */
fun Context.openInAppStore(packageName: String = this.packageName) {
    val intent = Intent(Intent.ACTION_VIEW)
    try {
        intent.data = Uri.parse("market://details?id=$packageName")
        startActivity(intent)
    } catch (ifPlayStoreNotInstalled: ActivityNotFoundException) {
        intent.data =
            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        startActivity(intent)
    }
}

/**
 * Open app by [packageName]
 */
fun Context.openApp(packageName: String) =
    packageManager.getLaunchIntentForPackage(packageName)?.run { startActivity(this) }

/**
 * Uninstall app by [packageName]
 */
fun Context.uninstallApp(packageName: String) {
    Intent(Intent.ACTION_DELETE).run {
        data = Uri.parse("package:$packageName")
        startActivity(this)
    }
}

/**
 * Send email
 * @param email the email address be sent to
 * @param subject a constant string holding the desired subject line of a message, @see [Intent.EXTRA_SUBJECT]
 * @param text a constant CharSequence that is associated with the Intent, @see [Intent.EXTRA_TEXT]
 */
fun Context.sendEmail(email: String, subject: String?, text: String?) {
    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")).run {
        subject?.let { putExtra(Intent.EXTRA_SUBJECT, subject) }
        text?.let { putExtra(Intent.EXTRA_TEXT, text) }
        startActivity(this)
    }
}

/**
 * 界面跳转
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */

inline fun <reified T : AppCompatActivity> Fragment.startPage(vararg params: Pair<String, Any?>) {
    val intent = Intent(this.activity, T::class.java).fillIntentArguments(*params)
    if (this !is AppCompatActivity) {
        intent.newTask()
    }
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Context.startPage(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java).fillIntentArguments(*params)
    if (this !is AppCompatActivity) {
        intent.newTask()
    }
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Activity.startPageForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) {
    val intent = Intent(this, T::class.java).fillIntentArguments(*params)
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Any> Context?.intentFor(vararg params: Pair<String, Any?>): Intent {
    return Intent(this, T::class.java).fillIntentArguments(*params)
}

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.newTask()
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

inline fun <reified T : BaseActivity<*, *>> BaseFragment<*, *>.startPageForResult(
    vararg params: Pair<String, Any?>,
    crossinline onResult: (code: Int, data: Intent?) -> Unit
) {

    T::class.startForResult({
        fillIntentArguments(*params)
    }) { code, data ->
        onResult(code, data)
    }
}

inline fun <reified T : BaseActivity<*, *>> BaseActivity<*, *>.startPageForResult(
    vararg params: Pair<String, Any?>,
    crossinline onResult: (code: Int, data: Intent?) -> Unit
) {
    T::class.startForResult({
        fillIntentArguments(*params)
    }) { code, data ->
        onResult(code, data)
    }
}