package com.wrbug.datafinder.server

import android.annotation.SuppressLint
import android.content.Context
import com.amitshekhar.DebugDB
import com.amitshekhar.debug.encrypt.sqlite.DebugDBEncryptFactory
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

    private var listeners = ArrayList<ServerStatusListener>()
    private fun getServer(): Server {
        if (mServer == null) {
            mServer = AndServer.serverBuilder(context)
                .inetAddress(InetAddress.getByName("0.0.0.0"))
                .listener(
                    object : Server.ServerListener {
                        override fun onException(e: Exception?) {
                            listeners.forEach {
                                it.onException(e)
                            }
                        }

                        override fun onStarted() {
                            listeners.forEach {
                                it.onStarted()
                            }
                        }

                        override fun onStopped() {
                            mServer = null
                            listeners.forEach {
                                it.onStopped()
                            }
                        }

                    })
                .port(ConfigDataManager.getServerPort())
                .timeout(10, TimeUnit.SECONDS)
                .build()
        }
        return mServer!!
    }


    fun addListener(listener: ServerStatusListener?): ServerManager {
        if (listener != null) {
            listeners.add(listener)
        }
        return this
    }

    fun removeListener(listener: ServerStatusListener?) {
        if (listener != null) {
            listeners.remove(listener)
        }
    }

    fun startServer(): ServerManager {
        if (!getServer().isRunning) {
            getServer().startup()
        }
        DebugDB.initialize(
            context,
            DebugDBEncryptFactory(),
            ConfigDataManager.getDatabaseServerPort()
        )
        return this
    }

    fun stopServer() {
        if (getServer().isRunning) {
            getServer().shutdown()
        }
        DebugDB.shutDown()
    }

    fun restartServer() {
        stopServer()
        startServer()
    }
}