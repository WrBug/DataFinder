package com.wrbug.datafinder.server.dao

import com.wrbug.datafinder.server.dao.table.DownloadInfo
import java.io.File

object DBManager {
    fun saveDownloadFile(file: File) {
        val hashCode = file.hashCode()
        saveDownloadFile(hashCode, file)
    }

    fun saveDownloadFile(downloadId: Int?, file: File) {
        val info = DownloadInfo()
        info.downloadId = downloadId?.toLong()
        info.path = file.absolutePath
        DataFinderDB.getDownloadInfoDao()?.insertOrReplace(info)
    }

    fun getDownloadFile(downloadId: Int?): DownloadInfo? {
        if (downloadId == null) {
            return null
        }
        val info = DataFinderDB.getDownloadInfoDao()?.queryBuilder()
            ?.where(DownloadInfoDao.Properties.DownloadId.eq(downloadId.toLong()))?.unique()
        return info
    }
}