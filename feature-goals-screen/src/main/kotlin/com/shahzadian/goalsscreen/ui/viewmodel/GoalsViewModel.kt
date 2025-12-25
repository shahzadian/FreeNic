package com.shahzadian.goalsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.goalsscreen.ui.AchievementsConst
import com.shahzadian.goalsscreen.ui.AchievementsUiModelFetcher
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GoalsViewModel @Inject constructor(
    private val achievementsUiModelFetcher: AchievementsUiModelFetcher,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val savingsGoalHelper: SavingsGoalHelper,
) : BaseViewModel<GoalsViewState, GoalsViewEvent, GoalsViewCommand>() {

    override val initState: GoalsViewState by lazyUnsafe { GoalsViewState.Loading }

    init {
        setDataState()
    }

    override fun onViewEventHappened(event: GoalsViewEvent) {
        when (event) {
            is GoalsViewEvent.OnSavingsGoalsUpgradeClicked -> {
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
                viewModelScope.launch {
                    sendViewCommand(
                        GoalsViewCommand.NavigateToAchievementShareScreen(
                            id = event.id
                        )
                    )
                }
            }

            is GoalsViewEvent.OnSavingsGoalCreateClicked -> {
                viewModelScope.launch {
                    sendViewCommand(
                        GoalsViewCommand.NavigateTo(
                            appScreen = AppScreen.SavingsGoalCreateScreen,
                        )
                    )
                }
            }


        }
    }

    private fun setDataState() {
        viewModelScope.launch {
            val achievementsResult = achievementsUiModelFetcher.fetch()
            setViewState(
                GoalsViewState.Data(
                    hasPaidSubscription = getUserCurrentSubscriptionUseCase() != null,
                    achievementsData = AchievementsData(
                        achievements = achievementsResult.achievements,
                        shouldShowMoreAchievementsButton = achievementsResult.initialAchievementsCount > AchievementsConst.MAX_ACHIEVEMENTS_ON_SCREEN,
                    ),
                    savingsGoal = savingsGoalHelper.getCurrentSavingsGoal()?.toUiModel(),
                )
            )
        }
    }

    private suspend fun SavingsGoal.toUiModel(): GoalsViewState.SavingsGoalUiModel {
        val category = savingsGoalHelper.getAllCategories()
            .firstOrNull { it.id == this.categoryId }
            ?.name
            ?: throw IllegalStateException("No category found for id ${this.categoryId}")
        return GoalsViewState.SavingsGoalUiModel(
            name = this.name,
            achievedPercents = this.achievedPercents.toInt(),
            category = category,
            necessaryAmount = this.necessaryAmount,
            currentAmount = this.currentAmount
        )
    }
}
