package com.wrbug.datafinder.util

import android.app.Dialog
import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import com.wrbug.datafinder.data.GlobalEnv

fun Context.dp2px(dpVal: Float): Int = UiUtils.dp2px(dpVal)
fun Fragment.dp2px(dpVal: Float): Int = UiUtils.dp2px(dpVal)
fun Dialog.dp2px(dpVal: Float): Int = UiUtils.dp2px(dpVal)
fun View.dp2px(dpVal: Float): Int = UiUtils.dp2px(dpVal)

object UiUtils {
    private val context: Context by lazy {
        GlobalEnv.appContext
    }

    fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal,
            context.resources?.displayMetrics
        ).toInt()
    }

    fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(2, spVal, context.resources?.displayMetrics).toInt()
    }

    fun px2dp(pxVal: Float): Float {
        val scale = context.resources?.displayMetrics?.density!!
        return pxVal / scale
    }

    fun px2sp(pxVal: Float): Float {
        return pxVal / context.resources?.displayMetrics?.scaledDensity!!
    }

    /**
     * 获取屏幕高度
     */
    fun getDeviceHeight(): Int {
        val dm = context.resources?.displayMetrics
        return dm?.heightPixels ?: 0
    }

    /**
     * 获取屏幕宽度
     */
    fun getDeviceWidth(): Int {
        val dm = context.resources?.displayMetrics
        return dm?.widthPixels ?: 0
    }

    /**
     * 获取状态栏的高度
     */
    fun getStatusHeight(): Int {
        var result: Int? = 10
        val resourceId =
            context.resources?.getIdentifier("status_bar_height", "dimen", "android") ?: 0
        if (resourceId > 0) {
            result = context.resources?.getDimensionPixelOffset(resourceId)
        }
        return result ?: 0
    }
}