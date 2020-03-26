package com.wrbug.datafinder.server.api

import android.text.TextUtils
import com.wrbug.datafinder.preview.PreviewManager
import com.wrbug.datafinder.server.cookie.CookieManager
import com.wrbug.datafinder.server.dao.DBManager
import com.wrbug.datafinder.server.data.home.HomeInfoListProvider
import com.wrbug.datafinder.server.download.FileCache
import com.wrbug.datafinder.server.type.FileType
import com.wrbug.datafinder.server.type.IconType
import com.wrbug.datafinder.server.vo.*
import com.yanzhenjie.andserver.annotation.RequestMapping
import com.yanzhenjie.andserver.annotation.RequestMethod
import com.yanzhenjie.andserver.annotation.RequestParam
import com.yanzhenjie.andserver.annotation.RestController
import com.yanzhenjie.andserver.http.HttpRequest
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
        "/api/history",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getHistory(httpRequest: HttpRequest): List<IFileInfo> {
        return DBManager.getUserHistoryList(CookieManager.getDeviceId(httpRequest))
            .map { IFileInfo.getFile(it.path) }
    }

    @RequestMapping(
        "/api/get_file_info",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getFileInfo(@RequestParam("file") filePath: String, httpRequest: HttpRequest): FileInfo {
        return File(filePath).run {
            if (isFile.not()) {
                throw Exception("获取文件错误")
            }
            DBManager.saveUserHistory(CookieManager.getDeviceId(httpRequest), filePath)
            FileInfo(this)
        }
    }

    @RequestMapping(
        "/api/get_file_preview",
        method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS]
    )
    fun getFilePreview(@RequestParam("id") fileId: String, httpRequest: HttpRequest): FilePreviewInfo {
        val file = FileCache.getFile(fileId.toInt()) ?: throw Exception("获取文件错误")
        val info = FilePreviewInfo()
        info.type = IconType.get(file)
        info.raw = PreviewManager.getRaw(file)
        return info
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