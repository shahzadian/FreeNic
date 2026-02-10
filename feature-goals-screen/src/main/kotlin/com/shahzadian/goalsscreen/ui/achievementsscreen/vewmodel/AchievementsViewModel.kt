package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AchievementsViewModel @Inject constructor(
    private val analyticsEventSender: AnalyticsEventSender,
    private val achievementsUiModelFetcher: AchievementsUiModelFetcher,
) : BaseViewModel<AchievementsViewState, AchievementsViewEvent, AchievementsViewCommand>() {

    override val initState: AchievementsViewState by lazyUnsafe { AchievementsViewState.Loading }

    init {
        setDataState()
    }

    override fun onViewEventHappened(event: AchievementsViewEvent) {
        when (event) {

            is AchievementsViewEvent.OnAchievementClicked -> {
                viewModelScope.launch {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.ACHIEVEMENT_TAP,
                            params = mapOf(
                                AnalyticsEventConst.ACHIEVEMENT_PARAM_ID to event.achievementId.asAnalyticsProperty()
                            )
                        )
                    )
                    sendViewCommand(
                        AchievementsViewCommand.NavigateToAchievementShareScreen(
                            id = event.achievementId
                        )
                    )
                }
            }

            is AchievementsViewEvent.OnAchievementShareClicked -> {
                viewModelScope.launch {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.ACHIEVEMENT_TAP,
                            params = mapOf(
                                AnalyticsEventConst.ACHIEVEMENT_PARAM_ID to event.achievementId.asAnalyticsProperty()
                            )
                        )
                    )
                    sendViewCommand(
                        AchievementsViewCommand.NavigateToAchievementShareScreen(
                            id = event.achievementId
                        )
                    )
                }
            }

            is AchievementsViewEvent.OnBackIconClicked -> {
                viewModelScope.launch {
                    sendViewCommand(AchievementsViewCommand.NavigateBack)
                }
            }
        }
    }

    private fun setDataState() {
        viewModelScope.launch {
            val achievementsResult = achievementsUiModelFetcher.fetch(
                config = AchievementsUiModelFetcher.Config(
                    maxCountsOnScreen = null
                )
            )
            setViewState(
                AchievementsViewState.Data(
                    achievementsData = AchievementsData(
                        achievements = achievementsResult.achievements,
                        shouldShowMoreAchievementsButton = false,
                    )
                )
            )
        }
    }
}
