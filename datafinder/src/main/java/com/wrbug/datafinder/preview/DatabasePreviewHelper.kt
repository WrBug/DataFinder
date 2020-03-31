package com.wrbug.datafinder.preview

import com.google.gson.annotations.SerializedName
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.util.NetWorkUtils
import java.io.File


/**
 *
 *  class: DatabasePreviewHelper.kt
 *  author: wrbug
 *  date: 2020-01-05
 *  description：
 *
 */
class DatabasePreviewHelper : PreviewHelper<DatabasePreviewHelper.PreviewResult> {
    override fun match(file: File): Boolean {
        return file.absolutePath.toLowerCase().endsWith(".db")
    }

    override fun getRaw(file: File): String {
        return ""
    }

    override fun getPreviewResult(file: File): PreviewResult? {
        val result = PreviewResult()
        result.inner = GlobalEnv.appContext.getDatabasePath(file.name) == file
        result.name = file.name
        result.path = file.absolutePath
        result.url =
            "http://${NetWorkUtils.getLocalIpAddress()}:${ConfigDataManager.getDatabaseServerPort()}/#db=${file.name}"
        return result
    }

    class PreviewResult {
        @SerializedName("inner")
        var inner: Boolean = true
        @SerializedName("name")
        var name = ""
        @SerializedName("path")
        var path = ""
        @SerializedName("url")
        var url = ""
    }
}