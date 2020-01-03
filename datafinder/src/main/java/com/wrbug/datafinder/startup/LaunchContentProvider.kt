package com.wrbug.datafinder.startup

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.server.ServerManager
import com.yanzhenjie.andserver.Server
import java.lang.Exception
import kotlin.concurrent.thread

class LaunchContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0
    override fun getType(uri: Uri) = uri.scheme
    override fun insert(uri: Uri, values: ContentValues?) = uri
    override fun onCreate(): Boolean {
        Log.i("AAAAAA", "LaunchContentProvider")
        GlobalEnv.init(context.applicationContext)
        ConfigDataManager.init(context.applicationContext)
        ServerManager.init(context.applicationContext)
        DataFinderService.start(context)
        ServerManager.instance.setListener(object : Server.ServerListener {
            override fun onException(e: Exception?) {
                e?.printStackTrace()
            }

            override fun onStarted() {
                Log.i("AAAAA", "onStarted")
            }

            override fun onStopped() {
                Log.i("AAAAA", "onStopped")
            }

        })
        thread {
            ServerManager.instance.startServer()
        }
        return true
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
