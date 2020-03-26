package com.wrbug.datafinder.startup

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.*
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.wrbug.datafinder.R
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.server.ServerManager
import com.wrbug.datafinder.server.ServerStatus
import com.wrbug.datafinder.server.ServerStatusListener
import com.wrbug.datafinder.ui.SettingActivity
import com.wrbug.datafinder.util.NetWorkUtils
import java.lang.Exception
import kotlin.concurrent.thread


/**
 *
 *  class: DataFinderService.kt
 *  author: wrbug
 *  date: 2019-12-30
 *  description：
 *
 */
class DataFinderService : Service() {
    companion object {
        private const val TAG = "DataFinderService"
        private const val ACTION_START_SERVER = "ACTION_START_SERVER"
        private const val ACTION_RESTART_SERVER = "ACTION_RESTART_SERVER"
        private const val ACTION_STOP_SERVER = "ACTION_STOP_SERVER"
        private const val CHANNEL_ID = "DEMON"
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= 26) {//Android8.0
                context.startForegroundService(Intent(context, DataFinderService::class.java))
            } else {
                context.startService(Intent(context, DataFinderService::class.java))
            }
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, DataFinderService::class.java))
        }

        fun startServer(context: Context) {
            context.sendBroadcast(Intent(ACTION_START_SERVER))
        }

        fun stopServer(context: Context) {
            context.sendBroadcast(Intent(ACTION_STOP_SERVER))
        }

        fun restartServer(context: Context) {
            context.sendBroadcast(Intent(ACTION_RESTART_SERVER))
        }
    }

    private var serverStatus = ServerStatus.Stop
    override fun onBind(intent: Intent): IBinder? = null
    private var restart = false
    override fun onCreate() {
        super.onCreate()
        registerReceiver()
        initServer()
        startServer()
    }

    private fun initServer() {
        ServerManager.init(applicationContext)
        ServerManager.instance.addListener(object : ServerStatusListener {
            override fun onException(e: Exception?) {
                Log.e(TAG, "onServerException", e)
                updateNotification()
                updateNotification(ServerStatus.StartFailed)
            }

            override fun onStarted() {
                Log.i(TAG, "onServerStarted")
                updateNotification(ServerStatus.Running)
            }

            override fun onStopped() {
                Log.i(TAG, "onServerStopped")
                updateNotification(ServerStatus.Stop)
                if (restart) {
                    restart = false
                    startServer()
                }
            }

        })
    }

    private fun startServer() {
        thread {
            ServerManager.instance.startServer()
        }
    }

    private fun stopServer() {
        thread {
            ServerManager.instance.stopServer()
        }
    }

    private fun restartServer() {
        thread {
            restart = true
            ServerManager.instance.stopServer()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initNotification()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.demon_process),
                    NotificationManager.IMPORTANCE_LOW
                )
            channel.enableLights(true)
            channel.setShowBadge(true)
            notificationManager.createNotificationChannel(channel)
        }
        updateNotification()
    }

    override fun onDestroy() {
        try {
            ServerManager.instance.stopServer()
            stopForeground(true)
            unregisterReceiver()
        } catch (th: Throwable) {

        }
        super.onDestroy()
    }

    private fun unregisterReceiver() {
        unregisterReceiver(networkStateReceiver)
    }

    private fun registerReceiver() {
        try {
            registerReceiver(
                networkStateReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
            registerReceiver(
                toggleServerReceiver,
                IntentFilter(ACTION_START_SERVER).apply {
                    addAction(ACTION_STOP_SERVER)
                    addAction(ACTION_RESTART_SERVER)
                }
            )
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

    private fun updateNotification(status: ServerStatus = ServerStatus.Stop) {
        try {
            serverStatus = status
            val intent = Intent(this, SettingActivity::class.java)
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val label = packageManager.getApplicationLabel(applicationInfo)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val content =
                "http://${NetWorkUtils.getLocalIpAddress()}:${ConfigDataManager.getServerPort()}"
            val statusText = when (status) {
                ServerStatus.Stop -> R.string.demon_process_sub_content_stop
                ServerStatus.Running -> R.string.demon_process_sub_content_running
                ServerStatus.StartFailed -> R.string.demon_process_sub_content_start_failed
            }
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setContentTitle(getString(R.string.data_finder_notification_name, label))
                .setContentText(content)
                .setSubText(getString(statusText))
                .setSmallIcon(R.drawable.ic_launcher)
                .setVibrate(null)
            val notification = builder.build()
            notification.flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR or
                    Notification.FLAG_FOREGROUND_SERVICE
            val id = (packageName.hashCode() and 53535) + 10000
            startForeground(id, notification)
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e(TAG, "updateNotification error", e)
            Log.i(TAG, "结束service")
            stopSelf()
        }
    }

    private val networkStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotification(serverStatus)
        }
    }

    private val toggleServerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_START_SERVER -> {
                    startServer()
                }
                ACTION_STOP_SERVER -> {
                    stopServer()
                }
                ACTION_RESTART_SERVER -> {
                    restartServer()
                }
            }
        }
    }


}