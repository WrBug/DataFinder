package com.wrbug.datafinder.preview

import java.io.File

object PreviewManager {
    private val list: Array<PreviewHelper<*>> = arrayOf(DatabasePreviewHelper())

    @JvmStatic
    fun match(file: File): Boolean {
        list.forEach {
            if (it.match(file)) {
                return true
            }
        }
        return false
    }
}