package com.wrbug.datafinder.server.data.home

import com.wrbug.datafinder.server.vo.HomeInfoVo


/**
 *
 *  class: HomeInfoListProvider.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
class HomeInfoListProvider : HomeInfoProvider {
    private val list = arrayOf(ExternalStorageDirInfoProvider(), DataDirInfoProvider())
    override fun getHomeInfo(): List<HomeInfoVo> {
        val arr = ArrayList<HomeInfoVo>()
        list.forEach {
            arr.addAll(it.getHomeInfo())
        }
        return arr
    }
}