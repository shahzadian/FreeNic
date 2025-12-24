package com.shahzadian.savingsgoalscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.savingsgoalscreen.domain.GetSavingsGoalCategoryUseCase
import com.shahzadian.savingsgoalscreen.domain.SavingsGoalCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalCreateViewModel @Inject constructor(
    private val getSavingsGoalCategoryUseCase: GetSavingsGoalCategoryUseCase,
) : BaseViewModel<SavingsGoalCreateViewState, SavingsGoalCreateViewEvent, SavingsGoalCreateViewCommand>() {

    override val initState: SavingsGoalCreateViewState by lazyUnsafe {
        SavingsGoalCreateViewState.Loading
    }

    private var goalName: String? = null
    private var goalAmount: String? = null
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
        }
    }

    private fun setData() {
        viewModelScope.launch {
//            TODO
            setViewState(
                SavingsGoalCreateViewState.Data(
                    goalName = goalName,
                    goalAmount = goalAmount,
                    categories = getSavingsGoalCategoryUseCase().map { it.toUiModel() },
                    selectedCategoryId = currentCategoryId
                )
            )
        }
    }

    private fun SavingsGoalCategory.toUiModel(): SavingsGoalCreateViewState.SavingsGoalCategoryUiModel {
        return SavingsGoalCreateViewState.SavingsGoalCategoryUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon
        )
    }
}