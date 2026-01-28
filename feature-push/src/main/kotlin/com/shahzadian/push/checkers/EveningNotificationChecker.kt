package com.shahzadian.push.checkers

import android.content.Context
import android.content.SharedPreferences
import com.freenic.CommonConst
import com.freenic.ui.AppScreen
import com.freenic.ui.model.CoroutineContexts
import com.shahzadian.baseui.R
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.api.PushNotificationData
import com.shahzadian.savingsgoal.domain.GetCurrentSavingsGoalUseCase
import dagger.Reusable
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

/**
 * Простой вечерний чекер: показывает ненавязчивое напоминание один раз в день вечером.
 * Требование: использовать те же title и iconRes, что и в AchievementsNotificationChecker.buildPush.
 */
@Reusable
class EveningNotificationChecker @Inject constructor(
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
    private val getCurrentSavingsGoalUseCase: GetCurrentSavingsGoalUseCase,
    @param:Named(CommonConst.PREF_NAME_APP_SETTINGS)
    private val sharedPreferences: SharedPreferences,
    private val coroutineContexts: CoroutineContexts,
) : NotificationChecker {

    override suspend fun check(context: Context): List<PushNotificationData> {
        return withContext(coroutineContexts.io) {
            val toReturn = mutableListOf<PushNotificationData>()
            val latestShownKey = sharedPreferences.getInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, -1)
            when (latestShownKey) {
                NOTIFICATION_ID_DAILY_CHECK_IN -> {
                    val savingsGoalData = getSavingsGoalData(context)
                    toReturn.add(savingsGoalData)
                    sharedPreferences.edit()
                        .putInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, NOTIFICATION_ID_SAVINGS_GOAL).apply()
                }

                NOTIFICATION_ID_SAVINGS_GOAL -> {
                    val dailyCheckIn = getDailyCheckInData(context)
                    dailyCheckIn?.let {
                        toReturn.add(it)
                        sharedPreferences.edit()
                            .putInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, NOTIFICATION_ID_DAILY_CHECK_IN)
                            .apply()
                    } ?: let {
                        val savingsGoalData = getSavingsGoalData(context)
                        toReturn.add(savingsGoalData)
                        sharedPreferences.edit()
                            .putInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, NOTIFICATION_ID_SAVINGS_GOAL)
                            .apply()
                    }
                }

                else -> {
                    val dailyCheckIn = getDailyCheckInData(context)
                    dailyCheckIn?.let {
                        toReturn.add(it)
                        sharedPreferences.edit()
                            .putInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, NOTIFICATION_ID_DAILY_CHECK_IN)
                            .apply()
                    } ?: let {
                        val savingsGoalData = getSavingsGoalData(context)
                        toReturn.add(savingsGoalData)
                        sharedPreferences.edit()
                            .putInt(PREF_KEY_LATEST_SHOWN_PUSH_ID, NOTIFICATION_ID_SAVINGS_GOAL)
                            .apply()
                    }
                }
            }

            return@withContext toReturn
        }
    }

    private suspend fun getDailyCheckInData(context: Context): PushNotificationData? {
        getDailyCheckInForDayUseCase(LocalDate.now())?.let {
            return null
        }

        val title = context.getString(R.string.push_daily_check_in_title)
        val message = context.getString(R.string.push_daily_check_in_desc)
        return PushNotificationData(
            notificationId = NOTIFICATION_ID_DAILY_CHECK_IN,
            channelId = CHANNEL_EVENING,
            title = title,
            message = message,
            iconRes = com.shahzadian.push.R.drawable.ic_push_icon, // тот же iconRes
            dedupKey = "evening_simple_reminder:${LocalDate.now()}",
            pendingIntent = PushNotificationData.getDefaultPendingIntent(context)
        )
    }

    private suspend fun getSavingsGoalData(context: Context): PushNotificationData {
        val pendingIntent = PushNotificationData.getDefaultPendingIntent(
            context = context,
            targetScreen = AppScreen.GoalsScreen,
        )
        return getCurrentSavingsGoalUseCase()?.let { goal ->
            if (goal.currentAmount == 0f) return@let null
            val savedAMount = "${goal.currentAmount}${goal.currency ?: ""}"
            PushNotificationData(
                notificationId = NOTIFICATION_ID_SAVINGS_GOAL,
                channelId = CHANNEL_EVENING,
                title = context.getString(R.string.push_savings_goal_existing_title),
                message = context.getString(R.string.push_savings_goal_existing_desc, savedAMount),
                iconRes = com.shahzadian.push.R.drawable.ic_push_icon,
                dedupKey = "evening_savings_goal:${LocalDate.now()}",
                pendingIntent = pendingIntent,
            )
        } ?: PushNotificationData(
            notificationId = NOTIFICATION_ID_SAVINGS_GOAL,
            channelId = CHANNEL_EVENING,
            title = context.getString(R.string.push_savings_goal_none_title),
            message = context.getString(R.string.push_savings_goal_none_desc),
            iconRes = com.shahzadian.push.R.drawable.ic_push_icon,
            dedupKey = "evening_savings_goal:${LocalDate.now()}",
            pendingIntent = pendingIntent,
        )
    }

    companion object {
        private const val NOTIFICATION_ID_DAILY_CHECK_IN = 1
        private const val NOTIFICATION_ID_SAVINGS_GOAL = 2
        const val CHANNEL_EVENING = "evening_reminders"
        const val PREF_KEY_LATEST_SHOWN_PUSH_ID = "latest_shown_push_id"
    }
}