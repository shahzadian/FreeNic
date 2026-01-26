package com.shahzadian.push.scheduler

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.shahzadian.push.worker.PushWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushScheduler @Inject constructor() {

    fun scheduleDaily(context: Context) {
        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .build()

        val targetHour = 10  // 10:00 локального времени
        val now = java.time.ZonedDateTime.now()
        var next = now.withHour(targetHour).withMinute(0).withSecond(0).withNano(0)
        if (!next.isAfter(now)) {
            next = next.plusDays(1)
        }
        val initialDelayMs = java.time.Duration.between(now, next).toMillis()
        val workRequest = PeriodicWorkRequestBuilder<PushWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag(WORK_TAG)
            .setInitialDelay(initialDelayMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    companion object {
        private const val UNIQUE_NAME = "feature_push_daily_work"
        const val WORK_TAG = "feature_push_tag"
    }
}
