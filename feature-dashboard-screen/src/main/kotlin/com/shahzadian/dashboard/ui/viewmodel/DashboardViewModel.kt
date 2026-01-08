package com.shahzadian.dashboard.ui.viewmodel

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor() :
    BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

    override val initState: DashboardViewState by lazyUnsafe {
        DashboardViewState.Loading
    }

    override fun onViewEventHappened(event: DashboardViewEvent) {

    }
}
