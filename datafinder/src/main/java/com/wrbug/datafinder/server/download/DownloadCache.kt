package com.wrbug.datafinder.server.download

import androidx.collection.LruCache
import com.wrbug.datafinder.server.dao.DBManager
import java.io.File

object DownloadCache {
    private val map = LruCache<Int, File>(1000)

    @JvmStatic
    fun getDownloadId(file: File): Int {
        val id = file.hashCode()
        map.put(id, file)
        DBManager.saveDownloadFile(id, file)
        return id
    }

    @JvmStatic
    fun getFile(id: Int): File? {

        var file = map[id]
        if (file != null) {
            return file
        }
        val path = DBManager.getDownloadFile(id)?.path
        if (path.isNullOrEmpty()) {
            return null
        }
        file = File(path)
        map.put(id, file)
        return file
    }

}