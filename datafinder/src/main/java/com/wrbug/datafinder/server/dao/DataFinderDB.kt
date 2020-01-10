package com.wrbug.datafinder.server.dao

import com.wrbug.datafinder.data.GlobalEnv


/**
 *
 *  class: DataFinderDB.kt
 *  author: wrbug
 *  date: 2020-01-09
 *  description：
 *
 */
object DataFinderDB {
    private val daoSession: DaoSession by lazy {
        DaoMaster(DBOpenHelper(GlobalEnv.appContext).writableDb).newSession()
    }

    fun getDownloadInfoDao(): DownloadInfoDao? {
        return daoSession.downloadInfoDao
    }

}