package com.wrbug.datafinder.server

import android.annotation.SuppressLint
import android.content.Context
import com.wrbug.datafinder.data.ConfigDataManager
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit


/**
 *
 *  class: ServerManager.kt
 *  author: wrbug
 *  date: 2019-12-29
 *  description：
 *
 */
class ServerManager {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        val instance: ServerManager by lazy {
            ServerManager()
        }

        fun init(ctx: Context) {
            context = ctx.applicationContext
        }
    }

    var mServer: Server? = null

    private var listener: Server.ServerListener? = null
    private fun getServer(): Server {
        if (mServer == null) {
            mServer = AndServer.serverBuilder(context)
                .inetAddress(InetAddress.getByName("0.0.0.0"))
                .listener(
                    object : Server.ServerListener {
                        override fun onException(e: Exception?) {
                            listener?.onException(e)
                        }

                        override fun onStarted() {
                            listener?.onStarted()
                        }

                        override fun onStopped() {
                            listener?.onStopped()
                        }

                    })
                .port(ConfigDataManager.getServerPort())
                .timeout(10, TimeUnit.SECONDS)
                .build()
        }
        return mServer!!
    }


    fun setListener(listener: Server.ServerListener?): ServerManager {
        this.listener = listener
        return this
    }

    fun startServer(): ServerManager {
        if (!getServer().isRunning) {
            getServer().startup()
        }
        return this
    }

    fun stopServer() {
        if (getServer().isRunning) {
            getServer().shutdown()
        } else {
        }
    }
}