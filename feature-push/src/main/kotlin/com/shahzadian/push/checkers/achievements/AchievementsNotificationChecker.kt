package com.shahzadian.push.checkers.achievements

import android.content.Context
import android.content.SharedPreferences
import com.freenic.CommonConst
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.achievements.domain.Achievement
import com.shahzadian.achievements.domain.AchievementState
import com.shahzadian.achievements.domain.AchievementStateProviderFactory
import com.shahzadian.achievements.domain.GetAchievementsUseCase
import com.shahzadian.baseui.R
import com.shahzadian.push.api.NotificationChecker
import com.shahzadian.push.api.PushNotificationData
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

/**
 * Чекер достижений: находит новые полностью достигнутые ачивки и формирует уведомления,
 * если такие ещё не были показаны ранее.
 */
@Reusable
class AchievementsNotificationChecker @Inject constructor(
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val achievementStateProviderFactory: AchievementStateProviderFactory,
    @param:Named(CommonConst.PREF_NAME_APP_SETTINGS)
    private val prefs: SharedPreferences,
) : NotificationChecker {

    override suspend fun check(context: Context): List<PushNotificationData> {
        val provider = achievementStateProviderFactory.create() ?: return emptyList()
        val achievements = getAchievementsUseCase()
        val alreadyShown =
            prefs.getStringSet(KEY_SHOWN_ACHIEVEMENT, emptySet())?.toMutableSet() ?: mutableSetOf()
        val achievementsToShow = achievements
            .filter { achievement ->
                val state = provider.getAchievementState(achievement)
                state is AchievementState.Unlocked && !alreadyShown.contains(achievement.id)
            }
        return achievementsToShow.lastOrNull()?.let { achievement ->
            alreadyShown.addAll(achievementsToShow.map { it.id })
            prefs.edit().putStringSet(KEY_SHOWN_ACHIEVEMENT, alreadyShown).apply()
            listOf(buildPush(context, achievement))
        } ?: emptyList()
    }

    private fun buildPush(context: Context, achievement: Achievement): PushNotificationData {
        val title = context.getString(R.string.push_achievement_title)
        val message = context.textFromWrapper(achievement.description)
        return PushNotificationData(
            notificationId = achievement.id.hashCode(),
            channelId = CHANNEL_ACHIEVEMENTS,
            title = title,
            message = message,
            iconRes = com.shahzadian.push.R.drawable.ic_push_icon,
            dedupKey = "achievement:${achievement.id}",
        )
    }

    companion object {
        private const val KEY_SHOWN_ACHIEVEMENT = "shown_achievements_ids"
        const val CHANNEL_ACHIEVEMENTS = "achievements"
    }
}
