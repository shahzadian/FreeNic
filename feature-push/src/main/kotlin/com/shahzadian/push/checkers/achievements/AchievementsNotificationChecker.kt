package com.shahzadian.push.checkers.achievements

import android.content.Context
import android.content.SharedPreferences
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementState
import com.shahzadian.achievements.domain.AchievementStateProviderFactory
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.api.PushNotificationData
import dagger.Reusable
import javax.inject.Inject

/**
 * Чекер достижений: находит новые полностью достигнутые ачивки и формирует уведомления,
 * если такие ещё не были показаны ранее.
 */
@Reusable
class AchievementsNotificationChecker @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val achievementStateProviderFactory: AchievementStateProviderFactory,
    private val prefs: SharedPreferences,
) : NotificationChecker {

    override suspend fun check(context: Context): List<PushNotificationData> {
        val provider = achievementStateProviderFactory.create() ?: return emptyList()
        val achievements = getAchievementsUseCase()
        val alreadyShown = prefs.getStringSet(KEY_SHOWN_ACHIEVEMENTS, emptySet())?.toMutableSet() ?: mutableSetOf()

        val toShow = mutableListOf<PushNotificationData>()
        achievements.forEach { achievement ->
            val state = provider.getAchievementState(achievement)
            if (state is AchievementState.Unlocked && !alreadyShown.contains(achievement.id)) {
                toShow += buildPush(context, achievement)
                alreadyShown += achievement.id
            }
        }

        if (toShow.isNotEmpty()) {
            prefs.edit().putStringSet(KEY_SHOWN_ACHIEVEMENTS, alreadyShown).apply()
        }
        return toShow
    }

    private fun buildPush(context: Context, achievement: Achievement): PushNotificationData {
        val title = context.getString(com.shahzadian.baseui.R.string.push_achievement_title)
        val description = context.textFromWrapper(achievement.name)
        val message = context.textFromWrapper(achievement.description)
        return PushNotificationData(
            notificationId = achievement.id.hashCode(),
            channelId = CHANNEL_ACHIEVEMENTS,
            title = title,
            message = message,
            dedupKey = "achievement:${achievement.id}",
        )
    }

    companion object {
        private const val KEY_SHOWN_ACHIEVEMENTS = "shown_achievements_ids"
        const val CHANNEL_ACHIEVEMENTS = "achievements"
    }
}
