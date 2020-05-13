package com.wrbug.datafinder.server.web

import android.content.Context
import com.yanzhenjie.andserver.annotation.Config
import com.yanzhenjie.andserver.framework.config.WebConfig
import java.io.File


/**
 *
 *  class: AppConfig.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description: web服务器配置
 *
 */

@Config
class AppConfig : WebConfig {


    override fun onConfig(context: Context?, delegate: WebConfig.Delegate?) {
        delegate?.addWebsite(WebController(context))
    }


}