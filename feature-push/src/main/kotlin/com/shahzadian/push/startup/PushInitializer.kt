package com.shahzadian.push.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.WorkManagerInitializer
import com.shahzadian.push.scheduler.PushScheduler
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * AndroidX Startup Initializer, который планирует ежедневную задачу WorkManager.
 */
class PushInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(context, PushInitializerEntryPoint::class.java)
        entryPoint.pushScheduler().scheduleDaily(context)
        entryPoint.pushScheduler().scheduleEvening(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> =
        mutableListOf(WorkManagerInitializer::class.java)
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PushInitializerEntryPoint {
    fun pushScheduler(): PushScheduler
}
