package com.sentox.demo.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.util.SparseArray
import android.view.View
import com.sentox.demo.function.base.log.Loger

/**
 * 正常编码中一般只会用到 [dp]/[sp] ;
 * 其中[dp]/[sp] 会根据系统分辨率将输入的dp/sp值转换为对应的px
 */
val Float.dp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

val Float.sp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
    )

val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

inline fun <T> SparseArray<T>.forEach(action: (T) -> Unit) {
    for (i in 0 until size())
        action(get(keyAt(i)))
}

/**
 * 判断某个应用是否已经安装
 *
 * @param pkg
 */
fun Context.isInstalled(pkg: String?): Boolean {
    if (pkg.isNullOrBlank()) return false
    try {
        return (null != packageManager.getApplicationInfo(pkg, PackageManager.GET_UNINSTALLED_PACKAGES))
    } catch (ex: Exception) {
        Loger.d("$pkg is not installed.")
    }
    return false
}

/**
 * 获取状态栏高度
 * @return
 */
fun View.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelOffset(resourceId)
    }
    return result
}

/**
 * 获取状态栏高度
 * @return
 */
fun Activity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelOffset(resourceId)
    }
    return result
}