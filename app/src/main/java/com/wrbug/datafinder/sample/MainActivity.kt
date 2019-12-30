package com.wrbug.datafinder.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wrbug.datafinder.server.ServerManager
import com.yanzhenjie.andserver.Server
import java.io.File
import java.lang.Exception
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ServerManager.init(applicationContext)
        ServerManager.instance.setListener(object : Server.ServerListener {
            override fun onException(e: Exception?) {
                e?.printStackTrace()
            }

            override fun onStarted() {
                Log.i("AAAAA", "onStarted")
            }

            override fun onStopped() {
                Log.i("AAAAA", "onStopped")
            }

        })
        thread {
            ServerManager.instance.startServer()
        }
    }
}
