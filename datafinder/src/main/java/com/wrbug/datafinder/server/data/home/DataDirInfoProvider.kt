package com.wrbug.datafinder.server.data.home

import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.server.type.IconType
import com.wrbug.datafinder.server.vo.HomeInfoVo
import java.io.File


/**
 *
 *  class: DataDirInfoProvider.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
class DataDirInfoProvider : HomeInfoProvider {
    companion object {
        private val applicationInfo = GlobalEnv.applicationInfo
        private val dataDir = applicationInfo.dataDir
    }

    override fun getHomeInfo(): List<HomeInfoVo> {
        val list = ArrayList<HomeInfoVo>()
        list.add(buildApkDirInfo())
        list.add(buildDatabaseInfo())
        list.add(buildSharedPreferenceInfo())
        list.add(buildDataAppDirInfo())
        list.add(buildDataDataDirInfo())
        return list
    }

    private fun buildDataDataDirInfo() = HomeInfoVo().apply {
        name = "data"
        icon = IconType.Dir
        path = dataDir
    }

    private fun buildDataAppDirInfo() = HomeInfoVo().apply {
        val dir = File(applicationInfo.sourceDir).parentFile
        name = "app"
        setPath(dir)
        icon = IconType.Dir
    }

    private fun buildApkDirInfo() = HomeInfoVo().apply {
        name = "${GlobalEnv.getAppName()}安装包"
        path = applicationInfo.sourceDir
        icon = IconType.Apk
    }

    private fun buildSharedPreferenceInfo() = HomeInfoVo().apply {
        name = "SharedPreference"
        icon = IconType.SharedPreference
        setPath(File(dataDir, "shared_prefs"))
    }

    private fun buildDatabaseInfo() = HomeInfoVo().apply {
        name = "数据库"
        setPath(File(dataDir, "databases"))
        icon = IconType.Database
    }
}