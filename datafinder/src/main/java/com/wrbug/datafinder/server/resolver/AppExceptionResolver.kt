package com.wrbug.datafinder.server.resolver

import com.wrbug.datafinder.server.ResponseData
import com.wrbug.datafinder.util.toJson
import com.yanzhenjie.andserver.annotation.Resolver
import com.yanzhenjie.andserver.error.MethodNotSupportException
import com.yanzhenjie.andserver.framework.ExceptionResolver
import com.yanzhenjie.andserver.framework.body.JsonBody
import com.yanzhenjie.andserver.http.HttpRequest
import com.yanzhenjie.andserver.http.HttpResponse


/**
 *
 *  class: AppExceptionResolver.kt
 *  author: wrbug
 *  date: 2020-01-06
 *  description：
 *
 */
@Resolver
class AppExceptionResolver : ExceptionResolver {
    override fun onResolve(request: HttpRequest, response: HttpResponse, e: Throwable) {
        response.setBody(JsonBody(ResponseData.failed(e.message ?: "服务器异常").toJson()))
    }
}