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
 *  description：
 *
 */
class WebController(private val context: Context?) : StorageWebsite(getPath(context)) {
    companion object {
        private const val WEB_PATH = "web"
        private fun getPath(context: Context?): String =
            File(context?.cacheDir, WEB_PATH).run {
                forceDelete()
                absolutePath
            } ?: ""
    }

    init {
        val list = getList("web")
        val dir=getPath(context)
        list.forEach {
            val dst = if (it.startsWith("web/")) it.substring(4) else it
            context?.assets?.open(it)?.readBytes()?.let { data ->
                File(dir, dst).apply {
                    if (!parentFile.exists()) {
                        parentFile.mkdirs()
                    }
                    writeBytes(data)
                }
            }
        }
    }

    private fun getList(path: String): List<String> {
        val list = ArrayList<String>()
        context?.run {
            assets.list(path)?.forEach {
                val newPath = "$path/$it"
                val l = getList(newPath)
                if (l.isNotEmpty()) {
                    list.addAll(l)
                } else {
                    list.add(newPath)
                }
            }
        }
        return list
    }
}