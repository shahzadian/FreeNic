package com.shahzadian.push.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shahzadian.push.entrypoints.PushEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker, который раз в день опрашивает все зарегистрированные чекеры
 * и централизованно показывает уведомления.
 */
class PushWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            PushEntryPoint::class.java
        )
        val dispatcher = entryPoint.pushDispatcher()
        val checkers = entryPoint.notificationCheckers()

        val results = mutableListOf<com.shahzadian.push.api.PushNotificationData>()
        for (checker in checkers) {
            kotlin.runCatching { checker.check(applicationContext) }
                .onSuccess { results += it }
        }
        if (results.isNotEmpty()) {
            dispatcher.showAll(applicationContext, results)
        }
        Result.success()
    }
}
