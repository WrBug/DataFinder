package com.wrbug.datafinder.server


/**
 *
 *  class: ResponseData.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description：
 *
 */
class ResponseData {
    private var success: Boolean = false
    private var data: Any? = null
    private var message: String? = null
    private var code: Int? = null

    companion object {
        fun success(data: Any?) = ResponseData().apply {
            success = true
            this.data = data
        }

        fun failed(message: String = "", code: Int? = 0) = ResponseData().apply {
            this.message = message
            this.code = code
        }
    }
}