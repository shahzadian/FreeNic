package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.goalsscreen.ui.AchievementsConst
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import com.shahzadian.shareimagescreen.domain.ImageShareController
import com.shahzadian.shareimagescreen.domain.ImageToShare
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GoalsViewModel @Inject constructor(
    private val achievementsUiModelFetcher: AchievementsUiModelFetcher,
    private val imageShareController: ImageShareController,
) : BaseViewModel<GoalsViewState, GoalsViewEvent, GoalsViewCommand>() {

    override val initState: GoalsViewState by lazyUnsafe { GoalsViewState.Loading }

    init {
        setDataState()
    }

    override fun onViewEventHappened(event: GoalsViewEvent) {
        when (event) {
            is GoalsViewEvent.OnSavingGoalsUpgradeClicked -> {
                viewModelScope.launch {
                    sendViewCommand(GoalsViewCommand.NavigateTo(AppScreen.PaywallScreen))
                }
            }

            is GoalsViewEvent.OnShowMoreAchievementsClicked -> {
                viewModelScope.launch {
                    sendViewCommand(GoalsViewCommand.NavigateTo(AppScreen.AchievementsScreen))
                }
            }

            is GoalsViewEvent.OnAchievementShareClicked -> {
                imageShareController.setImageToShare(
                    imageToShare = ImageToShare.ComposableContent(
                        content = event.achievementShareContent
                    )
                )
                viewModelScope.launch {
                    sendViewCommand(GoalsViewCommand.NavigateTo(AppScreen.ShareImageScreen))
                }
            }
        }
    }

    private fun setDataState() {
        viewModelScope.launch {
            val achievementsResult = achievementsUiModelFetcher.fetch()
            setViewState(
                GoalsViewState.Data(
                    achievementsData = AchievementsData(
                        achievements = achievementsResult.achievements,
                        shouldShowMoreAchievementsButton = achievementsResult.initialAchievementsCount > AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN,
                    )
                )
            )
        }
    }
}
