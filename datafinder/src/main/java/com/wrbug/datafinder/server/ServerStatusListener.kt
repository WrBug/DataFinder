package com.wrbug.datafinder.server

/**
 *
 *  class: ServerStatusListener.kt
 *  author: wrbug
 *  date: 2020-01-05
 *  description:
 *
 */
interface ServerStatusListener {

    /**
     * When the server is started.
     */
    fun onStarted()

    /**
     * When the server stops running.
     */
    fun onStopped()

    /**
     * An error occurred while starting the server.
     */
    fun onException(e: Exception?)
}