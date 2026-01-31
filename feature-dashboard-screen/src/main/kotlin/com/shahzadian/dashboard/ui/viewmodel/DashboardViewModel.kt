package com.shahzadian.dashboard.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.dailycheckin.domain.AddDailyCheckInStateUseCase
import com.shahzadian.dailycheckin.domain.DailyCheckInMood
import com.shahzadian.dailycheckin.domain.DailyCheckInState
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.domain.UserDashboard
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
    private val getMotivationalNotesUseCase: GetMotivationalNotesUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val getDailyCheckInForDayUseCase: GetDailyCheckInForDayUseCase,
    private val addDailyCheckInStateUseCase: AddDailyCheckInStateUseCase,
    private val journeyCalendarHelper: JourneyCalendarHelper,
) : BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

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

            is DashboardViewEvent.OnDailyCheckInClicked -> {
                onDailyCheckInClicked(result = event.result)
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val dashboard = getUserDashboardUseCase()
                ?: throw IllegalStateException("User dashboard is null")
            val todayData = journeyCalendarHelper.getTodayData()
            setViewState(
                state = DashboardViewState.Data(
                    userName = dashboard.userName,
                    analyticsData = dashboard.toMainProgress(),
                    motivationalNote = getMotivationalNotesUseCase().random().toUiModel(),
                    dailyCheckIn = getDailyCheckInState(),
                    calendar = DashboardViewState.CalendarUiModel(
                        todayDisplayableDate = todayData.todayDisplayableDate,
                        eventToShow = todayData.events.random(),
                    ),
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
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = AppScreen.CravingSupportScreen,
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
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = AppScreen.InsightsScreen,
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
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = AppScreen.JourneyCalendarScreen,
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

    private fun onDailyCheckInClicked(result: DashboardViewState.DailyCheckInResult) {
        viewModelScope.launch {
            (viewStateFlow.value as? DashboardViewState.Data)
                ?.let { state ->
                    val newState =
                        state.copy(dailyCheckIn = DashboardViewState.DailyCheckInUiModel.Confirmation)
                    setViewState(newState)
                }
            val mood = result.toMood()
            val checkInState = DailyCheckInState(
                mood = mood,
                date = LocalDate.now(),
            )
            addDailyCheckInStateUseCase(checkInState)
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.DASHBOARD_DAILY_CHECK_IN_TAP,
                    params = mapOf(
                        AnalyticsEventConst.DAILY_CHECK_IN_PARAM_VALUE_MOOD to mood.name.asAnalyticsProperty()
                    )
                )
            )
        }
    }

    private suspend fun getDailyCheckInState(): DashboardViewState.DailyCheckInUiModel {
        val today = LocalDate.now()
        getDailyCheckInForDayUseCase(today)
            ?.let {
                return DashboardViewState.DailyCheckInUiModel.Hide
            }
        return DashboardViewState.DailyCheckInUiModel.Selection
    }

    private fun DashboardViewState.DailyCheckInResult.toMood(): DailyCheckInMood {
        return when (this) {
            DashboardViewState.DailyCheckInResult.HARD -> DailyCheckInMood.HARD
            DashboardViewState.DailyCheckInResult.OKAY -> DailyCheckInMood.OKAY
            DashboardViewState.DailyCheckInResult.HAPPY -> DailyCheckInMood.HAPPY
        }
    }
}
