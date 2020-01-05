package com.wrbug.datafinder.startup

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv

class LaunchContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0
    override fun getType(uri: Uri) = uri.scheme
    override fun insert(uri: Uri, values: ContentValues?) = uri
    override fun onCreate(): Boolean {
        Log.i("AAAAAA", "LaunchContentProvider")
        GlobalEnv.init(context.applicationContext)
        ConfigDataManager.init(context.applicationContext)
        DataFinderService.start(context)
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
