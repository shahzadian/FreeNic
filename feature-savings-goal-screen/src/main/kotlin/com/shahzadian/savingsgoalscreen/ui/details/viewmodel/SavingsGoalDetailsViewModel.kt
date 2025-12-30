package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.savingsgoal.domain.SavingsGoalCategory
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalCategoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        setupObservers()
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
            daysLeft = this.daysLeft,
            amountLeft = this.amountLeft,
            category = savingsGoalHelper.getAllCategories().firstOrNull { category ->
                category.id == this.categoryId
            }?.toUiModel()
                ?: throw IllegalStateException("No category found for id ${this.categoryId}")
        )
    }

    private fun setupObservers() {
        savingsGoalHelper
            .eventFlow
            .onEach { setData() }
            .launchIn(viewModelScope)
    }

    private fun SavingsGoalCategory.toUiModel(): SavingsGoalCategoryUiModel {
        return SavingsGoalCategoryUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon
        )
    }
}
