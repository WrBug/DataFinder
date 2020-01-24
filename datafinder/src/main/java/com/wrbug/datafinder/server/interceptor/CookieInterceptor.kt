package com.wrbug.datafinder.server.interceptor

import com.wrbug.datafinder.server.cookie.CookieManager
import com.yanzhenjie.andserver.annotation.Interceptor
import com.yanzhenjie.andserver.framework.HandlerInterceptor
import com.yanzhenjie.andserver.framework.handler.RequestHandler
import com.yanzhenjie.andserver.http.HttpRequest
import com.yanzhenjie.andserver.http.HttpResponse


/**
 *
 *  class: CookieInterceptor.kt
 *  author: wrbug
 *  date: 2020-01-20
 *  description：
 *
 */
@Interceptor
class CookieInterceptor : HandlerInterceptor {

    override fun onIntercept(
        request: HttpRequest,
        response: HttpResponse,
        handler: RequestHandler
    ): Boolean {
        val deviceId = CookieManager.getDeviceId(request)
        if (deviceId.isEmpty()) {
            CookieManager.saveDeviceId(response)
        }
        return false
    }
}