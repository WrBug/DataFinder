package com.wrbug.datafinder.server.type

import android.support.annotation.Keep
import java.io.File

/**
 *
 *  class: IconType.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description:
 *
 */
@Keep
enum class MimeType {
    Unknown,
    Database,
    Xml,
    Apk,
    FILE,
    ;

    companion object {
        @JvmStatic
        fun get(file: File): MimeType {
            if (file.isDirectory) {
                return Unknown
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