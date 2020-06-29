package com.wrbug.datafinder.server

import com.google.gson.annotations.SerializedName


/**
 *
 *  class: ResponseData.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description:
 *
 */
class ResponseData {
    @SerializedName("success")
    private var success: Boolean = false
    @SerializedName("data")
    private var data: Any? = null
    @SerializedName("message")
    private var message: String? = null
    @SerializedName("code")
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