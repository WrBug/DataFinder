package com.wrbug.datafinder.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


val gson: Gson by lazy {
    Gson()
}

fun <T> String.fromJson(clazz: Class<T>): T? = fromJson(type = clazz)

inline fun <reified T> String.fromJson(): T? {
    return fromJson(object : TypeToken<T>() {}.type)
}

fun <T> String.fromJson(type: Type): T? {
    try {
        return gson.fromJson(this, type)
    } catch (t: Throwable) {

    }
    return null
}

fun Any?.toJson(): String {
    if (this == null) {
        return ""
    }
    try {
        return gson.toJson(this)
    } catch (t: Throwable) {

    }
    return ""
}