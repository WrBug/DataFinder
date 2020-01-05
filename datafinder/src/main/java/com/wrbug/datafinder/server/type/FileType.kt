package com.wrbug.datafinder.server.type

enum class FileType private constructor(private val type: Int) {
    FILE(0),
    DIRECTORY(1)
}