package com.wrbug.datafinder.server.api

import android.text.TextUtils
import com.wrbug.datafinder.server.vo.IFileInfo
import com.yanzhenjie.andserver.annotation.*
import com.yanzhenjie.andserver.util.MimeType
import java.io.File


/**
 *
 *  class: FileController.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description：
 *
 */
@RestController
class FileController {
    @GetMapping("/api/list")
    fun getList(@RequestParam("path", required = false) path: String?): List<IFileInfo> {
        val list = ArrayList<IFileInfo>()
        if (TextUtils.isEmpty(path)) {
            list.add(IFileInfo.getFile(File("/sdcard")))
            return list
        }
        File(path).run {
            if (isDirectory) {
                val type:MimeType
                listFiles()?.forEach {
                    list.add(IFileInfo.getFile(it))
                }
            }
        }
        return list
    }
}