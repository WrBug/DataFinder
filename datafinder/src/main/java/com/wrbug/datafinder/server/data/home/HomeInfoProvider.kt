package com.wrbug.datafinder.server.data.home

import com.wrbug.datafinder.server.vo.HomeInfoVo

/**
 *
 *  class: HomeInfoProvider.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
interface HomeInfoProvider {
    fun getHomeInfo(): List<HomeInfoVo>
}