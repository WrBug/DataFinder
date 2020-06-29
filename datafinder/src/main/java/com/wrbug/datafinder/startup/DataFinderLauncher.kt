package com.wrbug.datafinder.startup

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.amitshekhar.DebugDB
import com.amitshekhar.utils.FileUtils
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.util.FlexibleToastUtils
import java.io.File

object DataFinderLauncher {
    private var launched = false
    fun launch(context: Context) {
        if (launched) {
            return
        }
        launched = true
        DebugDB.initialize(context.applicationContext)
        GlobalEnv.init(context.applicationContext)
        FileUtils.assetsToFile("datafinder-web", File(context.cacheDir, "datafinder-web"))
        FlexibleToastUtils.init(context.applicationContext)
        ConfigDataManager.init(context.applicationContext)
        (context.applicationContext as? Application)?.registerActivityLifecycleCallbacks(callback)
    }


    private val callback = object : Application.ActivityLifecycleCallbacks {
        var started = false
        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {
            if (started) {
                return
            }
            //修复部分 android8.0/8.1 android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground()
            DataFinderService.start(activity?.applicationContext)
            removeCallback(activity)
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

    }

    private fun removeCallback(context: Context?) {
        (context?.applicationContext as? Application)?.unregisterActivityLifecycleCallbacks(callback)
    }

}