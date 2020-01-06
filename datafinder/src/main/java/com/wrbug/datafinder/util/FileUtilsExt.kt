package com.wrbug.datafinder.util

import java.io.File

fun File.forceDelete() {
    if (isDirectory.not()) {
        delete()
        return
    }
    listFiles()?.forEach {
        it?.forceDelete()
    }
    delete()
}