package com.wrbug.datafinder.server.cookie

import com.wrbug.datafinder.util.getMd5
import com.yanzhenjie.andserver.http.HttpRequest
import com.yanzhenjie.andserver.http.HttpResponse
import com.yanzhenjie.andserver.http.cookie.Cookie

object CookieManager {
    const val KEY_DEVICE_ID = "device_id"

    fun getDeviceId(request: HttpRequest) = request.getCookie(KEY_DEVICE_ID)?.value ?: ""


    fun saveDeviceId(response: HttpResponse) {
        response.addCookie(Cookie(KEY_DEVICE_ID, generateDeviceId()))
    }

    private fun generateDeviceId(): String {
        val time = System.currentTimeMillis()
        val random = Math.random()
        val data = time.toString() + random.toString()
        return data.getMd5()
    }
}