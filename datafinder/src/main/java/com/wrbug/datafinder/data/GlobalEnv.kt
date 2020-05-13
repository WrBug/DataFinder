package com.wrbug.datafinder.data

import android.content.Context
import android.content.pm.ApplicationInfo
import com.wrbug.datafinder.server.ServerManager
import com.wrbug.datafinder.server.ServerStatus
import com.wrbug.datafinder.server.ServerStatusListener


/**
 *
 *  class: GlobalEnv.kt
 *  author: wrbug
 *  date: 2020-01-03
 *  description:
 *
 */
object GlobalEnv {
    lateinit var appContext: Context
    lateinit var applicationInfo: ApplicationInfo
    var serverStatus = ServerStatus.Stop
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
        applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        ServerManager.instance.addListener(object : ServerStatusListener {
            override fun onStarted() {
                serverStatus = ServerStatus.Running
            }

            override fun onStopped() {
                serverStatus = ServerStatus.Stop
            }

            override fun onException(e: Exception?) {
                serverStatus = ServerStatus.StartFailed
            }

        })
    }

    fun getAppName() = appContext.packageManager.getApplicationLabel(applicationInfo)
}