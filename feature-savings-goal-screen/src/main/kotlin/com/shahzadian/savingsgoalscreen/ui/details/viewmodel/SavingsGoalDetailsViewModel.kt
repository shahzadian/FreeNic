package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalDetailsViewModel @Inject constructor(
    private val savingsGoalHelper: SavingsGoalHelper,
) : BaseViewModel<SavingsGoalDetailsViewState, SavingsGoalDetailsViewEvent, SavingsGoalDetailsViewCommand>() {

    override val initState: SavingsGoalDetailsViewState by lazyUnsafe {
        SavingsGoalDetailsViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: SavingsGoalDetailsViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val currentSavingsGoal = savingsGoalHelper
                .getCurrentSavingsGoal()
                ?.toUiModel()
                ?: throw IllegalStateException("No active savings goal found")
            setViewState(
                SavingsGoalDetailsViewState.Data(
                    uiModel = currentSavingsGoal,
                )
            )
        }
    }

    private suspend fun SavingsGoal.toUiModel(): SavingsGoalDetailsViewState.SavingsGoalDataUiModel {
        return SavingsGoalDetailsViewState.SavingsGoalDataUiModel(
            name = this.name,
            currency = this.currency,
            currentAmount = this.currentAmount,
            necessaryAmount = this.necessaryAmount,
            noCigDays = this.noCigDays,
            achievedPercents = this.achievedPercents.toInt(),
            category = savingsGoalHelper.getAllCategories().firstOrNull { category ->
                category.id == this.categoryId
            }?.name ?: throw IllegalStateException("No category found for id ${this.categoryId}")
        )
    }
}
