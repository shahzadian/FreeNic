package com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AchievementsViewModel @Inject constructor(
    private val achievementsUiModelFetcher: AchievementsUiModelFetcher,
) : BaseViewModel<AchievementsViewState, AchievementsViewEvent, AchievementsViewCommand>() {

    override val initState: AchievementsViewState by lazyUnsafe { AchievementsViewState.Loading }

    init {
        setDataState()
    }

    override fun onViewEventHappened(event: AchievementsViewEvent) {}

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
