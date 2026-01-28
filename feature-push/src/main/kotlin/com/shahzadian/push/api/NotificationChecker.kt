package com.shahzadian.push.api

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.freenic.CommonConst
import com.freenic.ui.AppScreen

/**
 * Интерфейс «чекера» — класс, который проверяет некоторый признак и, при необходимости,
 * формирует данные для показа уведомления. Можно добавлять новые реализации, чтобы
 * поддерживать другие виды уведомлений.
 */
fun interface NotificationChecker {
    /**
     * Выполнить проверку и вернуть список уведомлений к показу.
     */
    suspend fun check(context: Context): List<PushNotificationData>
}

data class PushNotificationData(
    val notificationId: Int,
    val channelId: String,
    val title: String,
    val message: String,
    val iconRes: Int,
    // Ключ для дедупликации/хранения факта показа
    val dedupKey: String,
    val pendingIntent: PendingIntent,
) {

    companion object {
        fun getDefaultPendingIntent(
            context: Context,
            targetScreen: AppScreen? = null,
        ): PendingIntent {
            val launchIntent =
                context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    targetScreen?.let { putExtra(CommonConst.KEY_INTENT_TARGET_SCREEN, it.route) }
                }

            return PendingIntent.getActivity(
                context,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
