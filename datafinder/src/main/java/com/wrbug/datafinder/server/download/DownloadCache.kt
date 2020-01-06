package com.wrbug.datafinder.server.download

import androidx.collection.LruCache
import java.io.File

object DownloadCache {
    private val map = LruCache<Int, File>(1000)

    @JvmStatic
    fun getDownloadId(file: File): Int {
        var id = file.absolutePath.hashCode()
        map.get(id)?.apply {
            if (file.equals(this).not()) {
                id += file.name.hashCode()
            }
        }
        map.put(id, file)
        return id
    }

    @JvmStatic
    fun getFile(id: Int): File? {
        return map[id]
    }

}