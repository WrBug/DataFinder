package com.wrbug.datafinder.startup

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.*
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.wrbug.datafinder.R
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.data.GlobalEnv
import com.wrbug.datafinder.server.ServerManager
import com.wrbug.datafinder.ui.SettingActivity
import com.wrbug.datafinder.util.NetWorkUtils
import com.yanzhenjie.andserver.Server
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
    }

    private var serverStatus = ServerStatus.Stop
    private lateinit var notification: Notification
    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        registerReceiver()
        startServer()
    }

    private fun startServer() {
        ServerManager.init(applicationContext)
        ServerManager.instance.setListener(object : Server.ServerListener {
            override fun onException(e: Exception?) {
                Log.e(TAG, "onServerException", e)
                updateNotification()
                updateNotification(ServerStatus.LunchFailed)
            }

            override fun onStarted() {
                Log.i(TAG, "onServerStarted")
                updateNotification(ServerStatus.Running)
            }

            override fun onStopped() {
                Log.i(TAG, "onServerStopped")
                updateNotification(ServerStatus.Stop)
            }

        })
        thread {
            ServerManager.instance.startServer()
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
            unregisterReceiver()
            stopForeground(true)
        } catch (th: Throwable) {

        }
        super.onDestroy()
    }

    private fun unregisterReceiver() {
        unregisterReceiver(networkStateReceiver)
    }

    private fun registerReceiver() {
        registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun updateNotification(status: ServerStatus = ServerStatus.Stop) {
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
            ServerStatus.LunchFailed -> R.string.demon_process_sub_content_lunch_failed
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.name, label))
            .setContentText(content)
            .setSubText(getString(statusText))
            .setSmallIcon(R.drawable.ic_launcher)
            .setVibrate(null)
        notification = builder.build()
        notification.flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR or
                Notification.FLAG_FOREGROUND_SERVICE
        startForeground(0x10000, notification)
    }

    private val networkStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotification(serverStatus)
        }
    }

    enum class ServerStatus {
        Running,
        Stop,
        LunchFailed
    }
}