package com.wrbug.datafinder.server.vo

import java.io.File

/**
 *
 *  class: IFileInfo.ktt
 *  author: wrbug
 *  date: 2019-12-30
 *  description:
 *
 */
interface IFileInfo {
    fun getPath(): String
    fun getSize(): Long

    companion object {
        fun getFile(file: String) = getFile(File(file))
        fun getFile(file: File): IFileInfo {
            if (file.isFile) {
                return FileInfo(file)
            }
            return DirectoryInfo(file)
        }
    }
}