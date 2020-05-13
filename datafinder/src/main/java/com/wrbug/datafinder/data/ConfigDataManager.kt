package com.wrbug.datafinder.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object ConfigDataManager {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private const val SP_NAME = "data_finder_config"
    private const val KEY_SERVER_PORT = "serverPort"
    private const val KEY_DATABASE_SERVER_PORT = "databaseServerPort"
    private var defaultServerPort = 2222
    private var defaultDbServerPort = 3333
    fun init(ctx: Context) {
        context = ctx.applicationContext
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        defaultServerPort = (context.packageName.hashCode() and 63535) + 2000
        defaultDbServerPort = (context.packageName.hashCode() and 63535) + 1999
    }

    fun saveServerPort(port: Int) {
        sharedPreferences.edit().putInt(KEY_SERVER_PORT, port)
            .apply()
    }

    fun getServerPort() = sharedPreferences.getInt(KEY_SERVER_PORT, defaultServerPort)


    fun saveDatabaseServerPort(port: Int) {
        sharedPreferences.edit().putInt(KEY_DATABASE_SERVER_PORT, port)
            .apply()
    }

    fun getDatabaseServerPort() =
        sharedPreferences.getInt(KEY_DATABASE_SERVER_PORT, defaultDbServerPort)
}