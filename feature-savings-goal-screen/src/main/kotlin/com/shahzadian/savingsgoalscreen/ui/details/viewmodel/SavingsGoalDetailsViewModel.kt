package com.shahzadian.savingsgoalscreen.ui.details.viewmodel

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SavingsGoalDetailsViewModel @Inject constructor() :
    BaseViewModel<SavingsGoalDetailsViewState, SavingsGoalDetailsViewEvent, SavingsGoalDetailsViewCommand>() {

    override val initState: SavingsGoalDetailsViewState by lazyUnsafe {
        SavingsGoalDetailsViewState.Loading
    }

    override fun onViewEventHappened(event: SavingsGoalDetailsViewEvent) {

    }
}
