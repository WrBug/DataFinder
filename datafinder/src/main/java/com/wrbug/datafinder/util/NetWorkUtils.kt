package com.wrbug.datafinder.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.wrbug.datafinder.data.GlobalEnv

object NetWorkUtils {

    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    fun checkEnable(paramContext: Context): Boolean {
        val i = false
        val localNetworkInfo = (paramContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return localNetworkInfo != null && localNetworkInfo.isAvailable
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(): String {
        return try {

            val wifiManager = GlobalEnv.appContext.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val i = wifiInfo.ipAddress
            int2ip(i)
        } catch (ex: Exception) {
            "0.0.0.0"
        }
    }
}
