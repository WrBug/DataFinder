package com.wrbug.datafinder.server.api

import android.text.TextUtils
import com.wrbug.datafinder.server.data.home.HomeInfoListProvider
import com.wrbug.datafinder.server.vo.FileInfo
import com.wrbug.datafinder.server.vo.HomeInfoVo
import com.wrbug.datafinder.server.vo.IFileInfo
import com.yanzhenjie.andserver.annotation.RequestMapping
import com.yanzhenjie.andserver.annotation.RequestMethod
import com.yanzhenjie.andserver.annotation.RequestParam
import com.yanzhenjie.andserver.annotation.RestController
import java.io.File
import java.lang.Exception


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

    @RequestMapping(
        "/api/home",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getHome(): List<HomeInfoVo> {
        val list = HomeInfoListProvider().getHomeInfo()
        return list
    }

    @RequestMapping(
        "/api/list",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getList(@RequestParam("path", required = false) path: String?): List<IFileInfo> {
        val list = ArrayList<IFileInfo>()
        if (TextUtils.isEmpty(path)) {
            list.add(IFileInfo.getFile(File("/sdcard")))
            return list
        }
        File(path).run {
            if (isDirectory) {
                listFiles()?.forEach {
                    list.add(IFileInfo.getFile(it))
                }
            }
        }
        return list
    }

    @RequestMapping(
        "/api/get_file_info",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getFileInfo(@RequestParam("file", required = false) filePath: String): FileInfo {
        return File(filePath).run {
            if (isFile.not()) {
                throw Exception("获取文件错误")
            }
            FileInfo(this)
        }
    }
}