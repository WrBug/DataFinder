package com.wrbug.datafinder.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper

import com.wrbug.datafinder.ui.widget.FlexibleToast

class FlexibleToastUtils private constructor() {
    companion object {
        // 全局的 handler 对象
        private val appHandler = Handler()
        // 全局的 Toast 对象
        @SuppressLint("StaticFieldLeak")
        private var flexibleToast: FlexibleToast? = null
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        fun init(context: Context) {
            mContext = context
            flexibleToast = FlexibleToast(context)
        }


        fun show(msg: String) {
            val builder = FlexibleToast.Builder(mContext!!)
            builder.setSecondText(msg)
            if (Looper.myLooper() != Looper.getMainLooper()) {
                appHandler.post { flexibleToast!!.toastShow(builder) }
            } else {
                flexibleToast!!.toastShow(builder)
            }
        }
    }
}
