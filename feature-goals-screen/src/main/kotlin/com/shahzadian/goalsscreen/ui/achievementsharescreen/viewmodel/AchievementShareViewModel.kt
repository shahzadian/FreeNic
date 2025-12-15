package com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AchievementShareViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val achievementsUiModelFetcher: AchievementsUiModelFetcher,
) : BaseViewModel<AchievementShareViewState, AchievementShareViewEvent, AchievementShareViewCommand>() {

    override val initState: AchievementShareViewState by lazyUnsafe { AchievementShareViewState.Loading }

    private val achievementId: String by lazyUnsafe {
        savedStateHandle[AppScreen.AchievementShareScreen.ARG_ACHIEVEMENT_ID]
            ?: throw IllegalArgumentException("No achievement id provided")
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: AchievementShareViewEvent) {
        when (event) {
            is AchievementShareViewEvent.OnAchievementShared -> {
                viewModelScope.launch {
                    sendViewCommand(AchievementShareViewCommand.NavigateBack)
                }
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val achievement = achievementsUiModelFetcher.getAchievementById(
                id = achievementId
            )
                ?: let {
                    sendViewCommand(
                        AchievementShareViewCommand.ShowError(
                            message = TextWrapper.FromRes(R.string.achievement_share_error)
                        )
                    )
                    sendViewCommand(AchievementShareViewCommand.NavigateBack)
                    return@launch
                }
            setViewState(
                AchievementShareViewState.Data(
                    achievementToShare = achievement
                )
            )
        }
    }
}
