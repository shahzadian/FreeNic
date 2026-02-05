package com.shahzadian.savingsgoalscreen.ui.create.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.ActivityEventSender
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
import com.freenic.ui.appinfo.appdatacache.AppDataCacheUpdater
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.savingsgoal.domain.SavingsGoalCategory
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalCategoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalCreateViewModel @Inject constructor(
    private val savingsGoalHelper: SavingsGoalHelper,
    private val savedStateHandle: SavedStateHandle,
    private val analyticsEventSender: AnalyticsEventSender,
    private val appDataCacheUpdater: AppDataCacheUpdater,
    private val activityEventSender: ActivityEventSender,
) : BaseViewModel<SavingsGoalCreateViewState, SavingsGoalCreateViewEvent, SavingsGoalCreateViewCommand>() {

    override val initState: SavingsGoalCreateViewState by lazyUnsafe {
        SavingsGoalCreateViewState.Loading
    }

    private var goalName: String? = null
    private var goalAmount: String? = null
    private var currentAmount: String? = null
    private var categories: List<SavingsGoalCategory>? = null
    private var currentCategoryId: String? = null
    private val isEdit: Boolean by lazyUnsafe {
        savedStateHandle.get<Boolean>(AppScreen.SavingsGoalCreateScreen.ARG_IS_EDIT) ?: false
    }

    init {
        viewModelScope.launch {
            if (isEdit) {
                // Prefill with current savings goal data if available
                val current = savingsGoalHelper.getCurrentSavingsGoal()
                if (current != null) {
                    goalName = current.name
                    goalAmount = current.necessaryAmount.toString()
                    currentAmount = current.currentAmount.toString()
                    currentCategoryId = current.categoryId
                }
            }
            setData()
        }
    }

    override fun onViewEventHappened(event: SavingsGoalCreateViewEvent) {
        when (event) {
            is SavingsGoalCreateViewEvent.OnGoalNameChanged -> {
                goalName = event.name
                setData()
            }

            is SavingsGoalCreateViewEvent.OnAmountChanged -> {
                goalAmount = event.amount
                setData()
            }

            is SavingsGoalCreateViewEvent.OnCurrentAmountChanged -> {
                currentAmount = event.amount
                setData()
            }

            is SavingsGoalCreateViewEvent.OnCategoryChanged -> {
                currentCategoryId = event.categoryId
                setData()
            }

            is SavingsGoalCreateViewEvent.OnSubmitClicked -> {
                onSavingsGoalSubmitted()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val isGoalAmountValid = goalAmount?.let { validateGoalAmount(it) }
                ?.let { it is SavingsGoalFieldValidationState.Valid } ?: false
            val isCurrentAmountValid = currentAmount?.let { validateCurrentAmount(it) }
                ?.let { it is SavingsGoalFieldValidationState.Valid } ?: false
            setViewState(
                SavingsGoalCreateViewState.Data(
                    model = SavingsGoalCreateDataUiModel(
                        goalName = goalName,
                        goalAmount = goalAmount,
                        currentAmount = currentAmount,
                        categories = getAllCategories().map { it.toUiModel() },
                        selectedCategoryId = currentCategoryId,
                        goalAmountValidationState = ::validateGoalAmount,
                        currentAmountValidationState = ::validateCurrentAmount,
                        isSubmitButtonEnabled = goalName?.isNotBlank() == true &&
                                isGoalAmountValid && isCurrentAmountValid &&
                                currentCategoryId != null
                    ),
                )
            )
        }
    }

    private fun onSavingsGoalSubmitted() {
        viewModelScope.launch {
            saveGoal()
            appDataCacheUpdater.onSavingsGoalCreated()
            activityEventSender.sendEvent(ActivityEvent.AppRateViewShowRequest)
            sendViewCommand(SavingsGoalCreateViewCommand.NavigateBack)
        }
    }

    private fun SavingsGoalCategory.toUiModel(): SavingsGoalCategoryUiModel {
        return SavingsGoalCategoryUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon
        )
    }

    private suspend fun getAllCategories(): List<SavingsGoalCategory> {
        return categories ?: let {
            val categories = savingsGoalHelper.getAllCategories()
            this.categories = categories
            categories
        }
    }

    private fun validateGoalAmount(amount: String): SavingsGoalFieldValidationState {
        val isValid = amount.toFloatOrNull()?.let {
            it in 0f..MAX_GOAL_AMOUNT
        } ?: false
        return if (isValid) {
            SavingsGoalFieldValidationState.Valid
        } else {
            SavingsGoalFieldValidationState.Invalid(
                errorMessage = TextWrapper.FromRes(
                    com.shahzadian.baseui.R.string.savings_goal_create_amount_error,
                    listOf(MAX_GOAL_AMOUNT)
                )
            )
        }
    }

    private fun validateCurrentAmount(amount: String): SavingsGoalFieldValidationState {
        val goalAmount =
            goalAmount?.toFloatOrNull() ?: return SavingsGoalFieldValidationState.Invalid(
                errorMessage = TextWrapper.FromRes(
                    com.shahzadian.baseui.R.string.savings_goal_create_not_all_fields_correct_error,
                )
            )
        val isValid = amount.toFloatOrNull()?.let {
            it in 0f..goalAmount
        } ?: false
        return if (isValid) {
            SavingsGoalFieldValidationState.Valid
        } else {
            SavingsGoalFieldValidationState.Invalid(
                errorMessage = TextWrapper.FromRes(
                    com.shahzadian.baseui.R.string.savings_goal_create_amount_error,
                    listOf(goalAmount)
                )
            )
        }
    }

    private suspend fun saveGoal() {
        val name = goalName ?: throw IllegalStateException("Goal name is empty")
        val amount = goalAmount ?: throw IllegalStateException("Goal amount is empty")
        val currentAmount =
            currentAmount?.toFloatOrNull() ?: throw IllegalStateException("Current amount is empty")
        val category = categories
            ?.firstOrNull { it.id == currentCategoryId }
            ?: throw IllegalStateException("No category selected")
        if (isEdit) {
            savingsGoalHelper.updateSavingsGoal(
                name = name,
                amount = amount.toFloat(),
                categoryId = category.id,
                currentAmount = currentAmount,
            )
        } else {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SAVINGS_GOAL_CREATED,
                    params = mapOf(
                        AnalyticsEventConst.SAVINGS_GOAL_CREATED_PARAM_GOAL_PRICE to amount.asAnalyticsProperty(),
                        AnalyticsEventConst.SAVINGS_GOAL_CREATED_PARAM_CURRENT_AMOUNT to currentAmount.asAnalyticsProperty(),
                        AnalyticsEventConst.SAVINGS_GOAL_CREATED_PARAM_CATEGORY_ID to category.id.asAnalyticsProperty()
                    )
                )
            )
            savingsGoalHelper.createSavingsGoal(
                name = name,
                amount = amount.toDouble(),
                categoryId = category.id,
                currentAmount = currentAmount,
            )
        }
    }

    private companion object {
        const val MAX_GOAL_AMOUNT: Float = 1000f
    }
}
