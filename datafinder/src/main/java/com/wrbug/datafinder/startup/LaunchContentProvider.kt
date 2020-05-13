package com.wrbug.datafinder.startup

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import com.amitshekhar.DebugDB
import com.amitshekhar.utils.FileUtils
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.util.FlexibleToastUtils
import java.io.File

class LaunchContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0
    override fun getType(uri: Uri) = uri.scheme
    override fun insert(uri: Uri, values: ContentValues?) = uri
    override fun onCreate(): Boolean {
        DebugDB.initialize(context.applicationContext)
        GlobalEnv.init(context.applicationContext)
        FileUtils.assetsToFile("datafinder-web", File(context.cacheDir, "datafinder-web"))
        FlexibleToastUtils.init(context.applicationContext)
        ConfigDataManager.init(context.applicationContext)
        (context.applicationContext as? Application)?.registerActivityLifecycleCallbacks(callback)
        return true
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
            removeCallback()
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

    private fun removeCallback() {
        (context?.applicationContext as? Application)?.unregisterActivityLifecycleCallbacks(callback)
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = 0
}
