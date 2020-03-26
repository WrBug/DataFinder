package com.wrbug.datafinder.preview

import java.io.File

/**
 *
 *  class: PreviewHelper.kt
 *  author: wrbug
 *  date: 2020-01-05
 *  description：
 *
 */
interface PreviewHelper<T> {
    fun match(file: File): Boolean

    fun getRaw(file: File): String
}