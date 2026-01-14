package com.freenic.logger

interface AppLogger {

    fun logDebug(tag: String, message: String)

    fun logError(tag: String, message: String, throwable: Throwable? = null)
}
