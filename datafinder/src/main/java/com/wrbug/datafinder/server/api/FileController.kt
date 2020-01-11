package com.wrbug.datafinder.server.api

import android.text.TextUtils
import com.wrbug.datafinder.server.data.home.HomeInfoListProvider
import com.wrbug.datafinder.server.type.FileType
import com.wrbug.datafinder.server.vo.DirectoryInfo
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
        "/api/get_file_info",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getFileInfo(@RequestParam("file") filePath: String): FileInfo {
        return File(filePath).run {
            if (isFile.not()) {
                throw Exception("获取文件错误")
            }
            FileInfo(this)
        }
    }

    @RequestMapping(
        "/api/get_directory_info",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getDirectoryInfo(@RequestParam("directory") filePath: String): DirectoryInfo {
        return File(filePath).run {
            if (exists().not()) {
                throw Exception("目录不存在")
            }
            if (isDirectory.not()) {
                throw Exception("获取目录错误")
            }
            if (canRead().not()) {
                throw Exception("目录读取失败")
            }
            DirectoryInfo(this).apply {
                children?.run {
                    sortBy { it.name }
                    sortBy { it.fileType == FileType.FILE }
                }
            }
        }
    }
}