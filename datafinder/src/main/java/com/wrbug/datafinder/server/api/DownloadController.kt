package com.wrbug.datafinder.server.api

import com.wrbug.datafinder.server.download.FileCache
import com.yanzhenjie.andserver.annotation.*
import com.yanzhenjie.andserver.framework.body.FileBody
import com.yanzhenjie.andserver.http.HttpResponse
import java.lang.Exception


/**
 *
 *  class: DownloadController.kt
 *  author: wrbug
 *  date: 2020-01-05
 *  description:
 *
 */
@Controller
class DownloadController {
    companion object {
        const val downloadPath = "/download"
    }

    @GetMapping("${downloadPath}/{fileName}")
    fun download(
        @PathVariable("fileName") fileName: String, @QueryParam("id") downloadId: String, response: HttpResponse
    ) {
        var id = 0
        try {
            id = downloadId.toInt()
        } catch (e: Exception) {
            throw Exception("下载地址不存在")
        }
        val file = FileCache.getFile(id) ?: throw Exception("下载地址不存在")
        if (file.isFile.not() || file.canRead().not()) {
            throw Exception("${fileName}无法下载")
        }
        val body = FileBody(file)
        response.setBody(body)
    }
}