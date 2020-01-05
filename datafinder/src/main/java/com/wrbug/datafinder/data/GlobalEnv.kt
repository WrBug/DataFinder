package com.wrbug.datafinder.data

import android.content.Context
import android.content.pm.ApplicationInfo


/**
 *
 *  class: GlobalEnv.kt
 *  author: wrbug
 *  date: 2020-01-03
 *  description：
 *
 */
object GlobalEnv {
    lateinit var appContext: Context
    lateinit var applicationInfo: ApplicationInfo

    fun init(context: Context) {
        appContext = context.applicationContext
        applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
    }

    fun getAppName() = appContext.packageManager.getApplicationLabel(applicationInfo)
}