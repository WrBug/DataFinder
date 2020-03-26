package com.wrbug.datafinder.preview

import java.io.File

object PreviewManager {
    private val list: Array<PreviewHelper<*>> = arrayOf(
        DatabasePreviewHelper(),
        XmlPreviewHelper()
    )

    @JvmStatic
    fun match(file: File): Boolean {
        list.forEach {
            if (it.match(file)) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun getRaw(file: File): String {
        list.forEach {
            if (it.match(file)) {
                return it.getRaw(file)
            }
        }
        return ""
    }
}