package com.wrbug.datafinder.preview

import java.io.File


/**
 *
 *  class: DatabasePreviewHelper.kt
 *  author: wrbug
 *  date: 2020-01-05
 *  description:
 *
 */
class XmlPreviewHelper : PreviewHelper<Any> {
    override fun match(file: File): Boolean {
        return file.absolutePath.toLowerCase().endsWith(".xml")
    }

    override fun getRaw(file: File): String {
        return file.readText()
    }
}