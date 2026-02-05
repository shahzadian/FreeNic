package com.shahzadian.savingsgoalscreen.ui.share.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.savingsgoal.domain.SavingsGoal
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalCategoryUiModel
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalShareViewModel @Inject constructor(
    private val savingsGoalHelper: SavingsGoalHelper,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<SavingsGoalShareViewState, SavingsGoalShareViewEvent, SavingsGoalShareViewCommand>() {

    override val initState: SavingsGoalShareViewState by lazyUnsafe {
        SavingsGoalShareViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: SavingsGoalShareViewEvent) {
        when (event) {
            is SavingsGoalShareViewEvent.OnShareClicked -> {
                analyticsEventSender.send(
                    AnalyticsEvent(
                        name = AnalyticsEventConst.SAVINGS_GOAL_SHARE_TAP,
                        params = mapOf(
                            AnalyticsEventConst.SAVINGS_GOAL_SHARE_TAP_PARAM_TEMPLATE_ID to event.selectedTemplateId.asAnalyticsProperty()
                        )
                    )
                )
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val currentSavingsGoal = savingsGoalHelper
                .getCurrentSavingsGoal()
                ?.toUiModel()
                ?: throw IllegalStateException("No active savings goal found")
            setViewState(
                SavingsGoalShareViewState.Data(
                    dataUiModel = currentSavingsGoal,
                )
            )
        }
    }

    private suspend fun SavingsGoal.toUiModel(): SavingsGoalDataUiModel {
        val category = savingsGoalHelper.getAllCategories()
            .firstOrNull { it.id == this.categoryId }
            ?: throw IllegalStateException("No category found for id ${this.categoryId}")
        return SavingsGoalDataUiModel(
            name = this.name,
            necessaryAmount = this.displayableNecessaryAmount,
            currentAmount = this.displayableCurrentAmount,
            noCigDays = this.noCigDays,
            achievedPercents = this.achievedPercents.toInt(),
            daysLeft = this.daysLeft,
            amountLeft = this.displayableAmountLeft,
            category = SavingsGoalCategoryUiModel(
                id = category.id,
                name = category.name,
                icon = category.icon
            )
        )
    }
}
