package com.shahzadian.insightsscreen.ui.viewmodel

import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class InsightsViewModel @Inject constructor() : BaseViewModel<InsightsViewState, InsightsViewEvent, InsightsViewCommand>() {

    override val initState: InsightsViewState by lazy { InsightsViewState.Loading }

    override fun onViewEventHappened(event: InsightsViewEvent) {

    }
}