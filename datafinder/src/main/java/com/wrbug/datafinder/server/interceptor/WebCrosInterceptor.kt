package com.wrbug.datafinder.server.interceptor

import com.wrbug.datafinder.util.NetWorkUtils
import com.yanzhenjie.andserver.annotation.Interceptor
import com.yanzhenjie.andserver.framework.HandlerInterceptor
import com.yanzhenjie.andserver.framework.handler.RequestHandler
import com.yanzhenjie.andserver.http.HttpMethod
import com.yanzhenjie.andserver.http.HttpRequest
import com.yanzhenjie.andserver.http.HttpResponse


/**
 *
 *  class: WebCrosInterceptorptor.kt
 *  author: wrbug
 *  date: 2020-01-04
 *  description：
 *
 */
@Interceptor
class WebCrosInterceptor : HandlerInterceptor {
    override fun onIntercept(
        request: HttpRequest,
        response: HttpResponse,
        handler: RequestHandler
    ): Boolean {
        response.setHeader("access-control-allow-origin", request.getHeader("Origin") ?: "")
        if (request.method == HttpMethod.OPTIONS) {
            response.setHeader("access-control-allow-methods", "GET,POST,DELETE,PUT")
            response.setHeader(
                "access-control-allow-headers",
                request.getHeader("Access-Control-Request-Headers") ?: ""
            )
            response.setHeader("access-control-allow-credentials", "true")
            response.setHeader("access-control-max-age", "3600")
            response.setHeader("allow", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH")
            return true
        }
        return false
    }
}