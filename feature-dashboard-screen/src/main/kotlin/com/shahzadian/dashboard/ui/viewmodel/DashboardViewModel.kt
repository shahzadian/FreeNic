package com.shahzadian.dashboard.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.cravingsscreen.domain.CravingTool
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCase
import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.domain.UserDashboard
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
    private val getCravingToolsUseCase: GetCravingToolsUseCase,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val getMotivationalNotesUseCase: GetMotivationalNotesUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

    private var latestCravingToolId: String? = null
    private var _hasPaidSubscription: Boolean? = null

    init {
        setData()
    }

    override val initState: DashboardViewState by lazyUnsafe {
        DashboardViewState.Loading
    }

    override fun onViewEventHappened(event: DashboardViewEvent) {
        when (event) {
            is DashboardViewEvent.OnWantToSmokeClicked -> {
                onWantToSmokeClicked()
            }

            is DashboardViewEvent.OnDetailedInsightsClicked -> {
                onDetailedInsightsClicked()
            }

            is DashboardViewEvent.OnJourneyCalendarClicked -> {
                onJourneyCalendarClicked()
            }

            is DashboardViewEvent.OnMotivationalNoteRefreshClicked -> {
                onMotivationalNoteRefreshClicked()
            }

            is DashboardViewEvent.OnISmokedClicked -> {
                onISmokedClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val dashboard = getUserDashboardUseCase()
                ?: throw IllegalStateException("User dashboard is null")
            setViewState(
                state = DashboardViewState.Data(
                    analyticsData = dashboard.toMainProgress(),
                    motivationalNote = getMotivationalNotesUseCase().random().toUiModel(),
                )
            )
        }
    }

    private fun onMotivationalNoteRefreshClicked() {
        val state = viewState as? DashboardViewState.Data ?: return
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_MOTIVATION_NOTE_REFRESH_TAP,
                )
            )
            setViewState(
                state.copy(
                    motivationalNote = getMotivationalNotesUseCase().random().toUiModel(),
                )
            )
        }
    }

    private fun UserDashboard.toMainProgress(): DashboardViewState.DashboardAnalyticsUiModel {
        return DashboardViewState.DashboardAnalyticsUiModel(
            noCigDays = this.mainProgressData.noCigDays,
            savedMoney = this.mainProgressData.savedMoney,
            currency = this.mainProgressData.currency ?: "",
        )
    }

    private fun onWantToSmokeClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_WANT_TO_SMOKE_TAP,
                )
            )
            val cravingTools = getCravingToolsUseCase()
            val tool = cravingTools
                .filter {
                    it.id != latestCravingToolId &&
                            it.behavior is CravingTool.Behavior.NavigateToScreen
                }
                .shuffled()
                .firstOrNull() ?: return@launch
            latestCravingToolId = tool.id
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = (tool.behavior as CravingTool.Behavior.NavigateToScreen).screen,
                )
            )
        }
    }

    private fun onDetailedInsightsClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_DETAILED_INSIGHTS_TAP,
                )
            )
            val screen = if (hasUserPaidSubscription()) {
                AppScreen.InsightsScreen
            } else {
                AppScreen.PaywallScreen
            }
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = screen,
                )
            )
        }
    }

    private fun onJourneyCalendarClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_JOURNEY_CALENDAR_TAP,
                )
            )
            val screen = if (hasUserPaidSubscription()) {
                AppScreen.JourneyCalendarScreen
            } else {
                AppScreen.PaywallScreen
            }
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = screen,
                )
            )
        }
    }

    private fun onISmokedClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_I_SMOKED_TAP,
                )
            )
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = AppScreen.SlipConfirmationScreen,
                )
            )
        }
    }

    private fun MotivationalNote.toUiModel(): DashboardViewState.MotivationalNoteUiModel {
        return DashboardViewState.MotivationalNoteUiModel(
            text = this.text,
        )
    }

    private suspend fun hasUserPaidSubscription(): Boolean {
        return _hasPaidSubscription ?: let {
            val subscription = getUserCurrentSubscriptionUseCase()
            _hasPaidSubscription = subscription != null
            subscription != null
        }
    }
}
