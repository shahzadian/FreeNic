package com.shahzadian.push.checkers.evening

import android.content.Context
import com.shahzadian.baseui.R
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.api.PushNotificationData
import dagger.Reusable
import java.time.LocalDate
import javax.inject.Inject

/**
 * Простой вечерний чекер: показывает ненавязчивое напоминание один раз в день вечером.
 * Требование: использовать те же title и iconRes, что и в AchievementsNotificationChecker.buildPush.
 */
@Reusable
class EveningSimpleReminderChecker @Inject constructor(
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
) : NotificationChecker {

    override suspend fun check(context: Context): List<PushNotificationData> {
        getDailyCheckInForDayUseCase(LocalDate.now())?.let {
            return emptyList()
        }

        // Сообщение без новых ресурсов: используем существующую строку «Оставайтесь на пути»
        val title = context.getString(R.string.push_daily_check_in_title) // тот же title
        val message = context.getString(R.string.push_daily_check_in_desc)
        val data = PushNotificationData(
            notificationId = NOTIFICATION_ID,
            channelId = CHANNEL_EVENING,
            title = title,
            message = message,
            iconRes = com.shahzadian.push.R.drawable.ic_push_icon, // тот же iconRes
            dedupKey = "evening_simple_reminder:${LocalDate.now()}",
        )

        return listOf(data)
    }

    companion object {
        private const val NOTIFICATION_ID = 0xE31179 // произвольный стабильный id
        const val CHANNEL_EVENING = "evening_reminders"
    }
}
