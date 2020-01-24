package com.wrbug.datafinder.server.dao

import com.wrbug.datafinder.server.dao.table.DownloadInfo
import com.wrbug.datafinder.server.dao.table.UserHistoryInfo
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

    fun saveUserHistory(deviceId: String, file: String) {
        if (file.isEmpty() || deviceId.isEmpty()) {
            return
        }
        val info = UserHistoryInfo().apply {
            hId = deviceId + file
            this.deviceId = deviceId
            this.path = file
            this.updateTime = System.currentTimeMillis()
        }
        DataFinderDB.getUserHistoryInfoDao()?.insertOrReplace(info)
    }

    fun getUserHistoryList(deviceId: String, size: Int = 20): List<UserHistoryInfo> {
        val data = DataFinderDB.getUserHistoryInfoDao()?.queryBuilder()
            ?.where(UserHistoryInfoDao.Properties.DeviceId.eq(deviceId))
            ?.orderDesc(UserHistoryInfoDao.Properties.UpdateTime)?.limit(size)?.list()
            ?: emptyList()
        return data.filter {
            File(it.path).exists().apply {
                if (this.not()) {
                    DataFinderDB.getUserHistoryInfoDao()?.delete(it)
                }
            }
        }
    }
}