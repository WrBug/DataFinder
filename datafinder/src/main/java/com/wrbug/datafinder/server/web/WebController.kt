package com.wrbug.datafinder.server.web

import android.content.Context
import com.wrbug.datafinder.util.forceDelete
import com.yanzhenjie.andserver.framework.website.StorageWebsite
import java.io.File


/**
 *
 *  class: WebController.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description:
 *
 */
class WebController(private val context: Context?) : StorageWebsite(getPath(context)) {
    companion object {
        private const val WEB_PATH = "datafinder-web/datafinder"
        private fun getPath(context: Context?): String =
            File(context?.cacheDir, WEB_PATH).absolutePath
    }
}