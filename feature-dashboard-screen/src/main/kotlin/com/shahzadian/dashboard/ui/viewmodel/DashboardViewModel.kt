package com.shahzadian.dashboard.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.domain.UserDashboard
import com.shahzadian.user.domain.UserDataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
) : BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

    private var userDataHelper: UserDataHelper? = null

    init {
        setData()
    }

    override val initState: DashboardViewState by lazyUnsafe {
        DashboardViewState.Loading
    }

    override fun onViewEventHappened(event: DashboardViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val dashboard = getUserDashboardUseCase()
                ?: throw IllegalStateException("User dashboard is null")
            setViewState(dashboard.toViewState())
        }
    }

    private fun UserDashboard.toViewState(): DashboardViewState.Data {
        return DashboardViewState.Data(
            mainProgressDataUiModel = DashboardViewState.MainProgressDataUiModel(
                noCigDays = this.mainProgressData.noCigDays,
                savedMoney = this.mainProgressData.savedMoney,
                currency = this.mainProgressData.currency,
            )
        )
    }
}
