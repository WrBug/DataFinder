package com.wrbug.datafinder.server.type

import androidx.annotation.Keep
import java.io.File

/**
 *
 *  class: IconType.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
@Keep
enum class IconType {
    SdCard,
    Database,
    Xml,
    Dir,
    Apk,
    FILE,
    ;

    companion object {
        @JvmStatic
        fun get(file: File): IconType {
            if (file.isDirectory) {
                return Dir
            }
            val fileName = file.name.toLowerCase()
            return when {
                fileName.endsWith(".db") -> Database
                fileName.endsWith(".xml") -> Xml
                fileName.endsWith(".apk") -> Apk
                else -> FILE
            }
        }
    }
}