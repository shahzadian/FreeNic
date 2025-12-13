package com.shahzadian.dashboard.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedUiModel
import com.shahzadian.dashboard.ui.header.model.HeaderUiModel
import com.shahzadian.dashboard.ui.healthprogress.model.HealthProgressUiModel
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryUiModel
import com.shahzadian.dashboard.ui.nocigdays.model.NoCigDaysUiModel
import com.shahzadian.dashboard.ui.savedmoney.model.SavedMoneyUiModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
) :
    BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

    override val initState: DashboardViewState by lazyUnsafe { DashboardViewState.Loading }

    init {
        viewModelScope.launch {
            try {
                val dashboardData = composeDashboardData()
                setViewState(dashboardData)
            } catch (t: Throwable) {
//                Add error state
            }
        }
    }

    override fun onViewEventHappened(event: DashboardViewEvent) {
    }

    private suspend fun composeDashboardData(): DashboardViewState.Data {
        val user = getUserDataUseCase()
            ?: throw IllegalStateException("Couldn't get user data")
        val dashboard = getUserDashboardUseCase()
            ?: throw IllegalStateException("Couldn't get user dashboard data")
        return DashboardViewState.Data(
            uiModel = DashboardUiModel(
                headerUiModel = HeaderUiModel(
                    userName = user.personalData.name,
                ),
                noCigDaysUiModel = NoCigDaysUiModel(
                    days = dashboard.noCigDays,
                ),
                savedMoneyUiModel = SavedMoneyUiModel(
                    savedMoney = dashboard.savedMoney,
                    currency = dashboard.currency?.symbol,
                ),
                healthProgressUiModel = HealthProgressUiModel(
                    items = dashboard.healthProgressItems.map { item ->
                        HealthProgressUiModel.HealthProgressItemUiModel(
                            label = item.label,
                            icon = item.icon,
                            progress = item.percents,
                        )
                    }
                ),
                lungsRecoveryUiModel = LungsRecoveryUiModel(
                    recoveryPercentage = dashboard.lungHealthPercents,
                ),
                cigsAvoidedUiModel = CigsAvoidedUiModel(
                    count = dashboard.cigsAvoided
                )
            )
        )
    }
}
