package com.wrbug.datafinder.server.data.home

import android.os.Environment
import android.text.TextUtils
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.server.type.IconType
import com.wrbug.datafinder.server.vo.HomeInfoVo
import java.io.File


/**
 *
 *  class: ExternalStorageDirInfoProvider.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
class ExternalStorageDirInfoProvider : HomeInfoProvider {
    override fun getHomeInfo(): List<HomeInfoVo> {
        val list = ArrayList<HomeInfoVo>()
        list.add(buildSdcardInfo())
        list.add(buildDataInfo())
        return list
    }

    private fun buildDataInfo() = HomeInfoVo().apply {
        val sdCard = GlobalEnv.appContext.externalCacheDir?.parentFile
        name = "Data"
        icon = IconType.Dir
        setPath(sdCard)
    }

    private fun buildSdcardInfo() = HomeInfoVo().apply {
        val sdCard = getSdcardDir()
        name = "内部存储"
        icon = IconType.SdCard
        setPath(sdCard)
    }

    private fun getSdcardDir(): File {
        return if (TextUtils.equals(
                Environment.getExternalStorageState(),
                Environment.MEDIA_MOUNTED
            )
        ) {
            Environment.getExternalStorageDirectory()
        } else {
            GlobalEnv.appContext.filesDir
        }
    }
}