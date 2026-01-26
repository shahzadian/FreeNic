package com.shahzadian.push.api

import android.content.Context

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
    // Ключ для дедупликации/хранения факта показа
    val dedupKey: String,
)
