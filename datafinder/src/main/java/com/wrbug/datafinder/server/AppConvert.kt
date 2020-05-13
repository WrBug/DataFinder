package com.wrbug.datafinder.server

import com.wrbug.datafinder.util.fromJson
import com.wrbug.datafinder.util.toJson
import com.yanzhenjie.andserver.annotation.Converter
import com.yanzhenjie.andserver.framework.MessageConverter
import com.yanzhenjie.andserver.framework.body.JsonBody
import com.yanzhenjie.andserver.http.ResponseBody
import com.yanzhenjie.andserver.util.MediaType
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset


/**
 *
 *  class: AppConvert.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description:
 *
 */
@Converter
class AppConvert : MessageConverter {
    override fun convert(output: Any, mediaType: MediaType?): ResponseBody {
        return JsonBody(ResponseData.success(output).toJson())
    }

    override fun <T : Any?> convert(stream: InputStream, mediaType: MediaType?, type: Type?): T? {
        val charset = mediaType?.charset ?: Charset.defaultCharset()
        val json = stream.readBytes().toString(charset)
        return json.fromJson(type!!)
    }
}