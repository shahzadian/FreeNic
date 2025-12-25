package com.shahzadian.savingsgoalscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.savingsgoal.domain.SavingsGoalCategory
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalCreateViewModel @Inject constructor(
    private val savingsGoalHelper: SavingsGoalHelper,
) : BaseViewModel<SavingsGoalCreateViewState, SavingsGoalCreateViewEvent, SavingsGoalCreateViewCommand>() {

    override val initState: SavingsGoalCreateViewState by lazyUnsafe {
        SavingsGoalCreateViewState.Loading
    }

    private var goalName: String? = null
    private var goalAmount: String? = null
    private var categories: List<SavingsGoalCategory>? = null
    private var currentCategoryId: String? = null

    init {
        setData()
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
            setViewState(
                SavingsGoalCreateViewState.Data(
                    goalName = goalName,
                    goalAmount = goalAmount,
                    categories = getAllCategories().map { it.toUiModel() },
                    selectedCategoryId = currentCategoryId,
                    isSubmitButtonEnabled = goalName?.isNotBlank() == true &&
                            goalAmount?.isNotBlank() == true &&
                            currentCategoryId != null
                )
            )
        }
    }

    private fun onSavingsGoalSubmitted() {
        viewModelScope.launch {
            saveGoal()
            sendViewCommand(SavingsGoalCreateViewCommand.NavigateBack)
        }
    }

    private fun SavingsGoalCategory.toUiModel(): SavingsGoalCreateViewState.SavingsGoalCategoryUiModel {
        return SavingsGoalCreateViewState.SavingsGoalCategoryUiModel(
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

    private suspend fun saveGoal() {
        val name = goalName ?: throw IllegalStateException("Goal name is empty")
        val amount = goalAmount ?: throw IllegalStateException("Goal amount is empty")
        val category = categories
            ?.firstOrNull { it.id == currentCategoryId }
            ?: throw IllegalStateException("No category selected")
        savingsGoalHelper.createSavingsGoal(
            name = name,
            amount = amount.toDouble(),
            categoryId = category.id,
        )
    }
}
