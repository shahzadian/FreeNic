package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.JourneyCalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class JourneyCalendarViewModel @Inject constructor(
    private val journeyCalendarHelper: JourneyCalendarHelper,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<JourneyCalendarViewState, JourneyCalendarViewEvent, JourneyCalendarViewCommand>() {

    override val initState: JourneyCalendarViewState by lazyUnsafe {
        JourneyCalendarViewState.Loading
    }

    private var tappetDay: DayCellUiModel? = null

    init {
        viewModelScope.launch {
            setData()
        }
    }

    override fun onViewEventHappened(event: JourneyCalendarViewEvent) {
        when (event) {
            is JourneyCalendarViewEvent.OnPreviousMonthClicked -> {
                onPrevMonthClicked()
            }

            is JourneyCalendarViewEvent.OnNextMonthClicked -> {
                onNextMonthClicked()
            }

            is JourneyCalendarViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }

            is JourneyCalendarViewEvent.OnDayTapped -> {
                onDayTapped(event.day)
            }

            is JourneyCalendarViewEvent.OnBottomSheetDismissRequired -> {
                onBottomSheetDismissRequired()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val model = journeyCalendarHelper.getMonth()
            setViewState(
                state = JourneyCalendarViewState.Data(
                    hasPreviousMonth = journeyCalendarHelper.hasPreviousMonth(),
                    tappedDay = tappetDay,
                    monthDataUiModel = model,
                )
            )
        }
    }

    private fun onPrevMonthClicked() {
        viewModelScope.launch {
            journeyCalendarHelper.decrementMonth()
            setData()
        }
    }

    private fun onNextMonthClicked() {
        viewModelScope.launch {
            journeyCalendarHelper.incrementMonth()
            setData()
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(
                JourneyCalendarViewCommand.NavigateBack
            )
        }
    }

    private fun onDayTapped(dayCellUiModel: DayCellUiModel) {
        tappetDay = dayCellUiModel
        val currentState = viewState as? JourneyCalendarViewState.Data ?: return
        viewModelScope.launch {
            setViewState(
                state = currentState.copy(tappedDay = dayCellUiModel)
            )
            val dayType = when {
                dayCellUiModel.isToday -> AnalyticsEventConst.PARAM_VALUE_DAY_TYPE_TODAY
                dayCellUiModel.isInFuture -> AnalyticsEventConst.PARAM_VALUE_DAY_TYPE_FUTURE
                else -> AnalyticsEventConst.PARAM_VALUE_DAY_TYPE_PAST
            }
            analyticsEventSender.send(
                event = AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP,
                    params = mapOf(
                        AnalyticsEventConst.PARAM_HAS_EVENTS to dayCellUiModel.events.isNotEmpty()
                            .asAnalyticsProperty(),
                        AnalyticsEventConst.PARAM_DAY_TYPE to dayType.asAnalyticsProperty(),
                    )
                ),
            )
        }
    }

    private fun onBottomSheetDismissRequired() {
        tappetDay = null
        val currentState = viewState as? JourneyCalendarViewState.Data ?: return
        viewModelScope.launch {
            setViewState(
                state = currentState.copy(tappedDay = null)
            )
        }
    }
}