package com.wrbug.datafinder.startup

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.wrbug.datafinder.R
import com.wrbug.datafinder.data.ConfigDataManager
import com.wrbug.datafinder.ui.SettingActivity
import com.wrbug.datafinder.util.NetWorkUtils


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
        const val FLOAT_BUTTON = "floatButton"
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

    private lateinit var notification: Notification
    override fun onBind(intent: Intent): IBinder? = null
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
        val intent = Intent(this, SettingActivity::class.java)
        val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
        val label = packageManager.getApplicationLabel(applicationInfo)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
            .setContentTitle(getString(R.string.name, label))
            .setContentText("http://${NetWorkUtils.getLocalIpAddress()}:${ConfigDataManager.getServerPort()}")
            .setSubText(getString(R.string.demon_process_sub_content))
            .setSmallIcon(R.drawable.ic_launcher)
            .setVibrate(null)
        notification = builder.build()
        notification.flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_NO_CLEAR or
                Notification.FLAG_FOREGROUND_SERVICE
        updateNotification()
    }

    override fun onDestroy() {
        try {
            stopForeground(true)
        } catch (th: Throwable) {

        }
        super.onDestroy()
    }

    private fun updateNotification() {
        startForeground(0x10000, notification)
    }


}