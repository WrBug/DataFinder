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
    companion object {
        private var autolAunch = true
        fun setAutoLaunch(auto: Boolean) {
            autolAunch = auto
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0
    override fun getType(uri: Uri) = uri.scheme
    override fun insert(uri: Uri, values: ContentValues?) = uri
    override fun onCreate(): Boolean {
        if (autolAunch) {
            DataFinderLauncher.launch(context)
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
