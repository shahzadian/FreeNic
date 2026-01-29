package com.shahzadian.journeycalendarscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.ActivityEventSender
import com.freenic.activityevent.OnAdEventResult
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.ad.AdHelper
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.DayCellUiModel
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.JourneyCalendarHelper
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class JourneyCalendarViewModel @Inject constructor(
    private val journeyCalendarHelper: JourneyCalendarHelper,
    private val analyticsEventSender: AnalyticsEventSender,
    private val activityEventSender: ActivityEventSender,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val adHelper: AdHelper,
) : BaseViewModel<JourneyCalendarViewState, JourneyCalendarViewEvent, JourneyCalendarViewCommand>() {

    override val initState: JourneyCalendarViewState by lazyUnsafe {
        JourneyCalendarViewState.Loading
    }

    private var tappetDay: DayCellUiModel? = null

    init {
        setData()
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

            is JourneyCalendarViewEvent.OnCalendarPreviewSubscribeClicked -> {
                onCalendarPreviewSubscribeClicked()
            }

            is JourneyCalendarViewEvent.OnWatchVideoClicked -> {
                onWatchVideoClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val hasPaidSubscription =
                getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription
            val hasRewardedAccess = journeyCalendarHelper.rewardHelper.hasAccessToCalendar()
            if (hasPaidSubscription || hasRewardedAccess) {
                val model = journeyCalendarHelper.getMonth()
                setViewState(
                    state = JourneyCalendarViewState.Data(
                        calendarAccessType = getCalendarAccessType(),
                        hasPreviousMonth = journeyCalendarHelper.hasPreviousMonth(),
                        tappedDay = tappetDay,
                        monthDataUiModel = model,
                    )
                )
            } else {
                setViewState(
                    JourneyCalendarViewState.Preview(
                        previewRewardState = JourneyCalendarViewState.PreviewRewardState.Default
                    )
                )
            }
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
                dayCellUiModel.isToday -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_TODAY
                dayCellUiModel.isInFuture -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_FUTURE
                else -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_PAST
            }
            analyticsEventSender.send(
                event = AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP, params = mapOf(
                        AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_HAS_EVENTS to dayCellUiModel.events.isNotEmpty()
                            .asAnalyticsProperty(),
                        AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_DAY_TYPE to dayType.asAnalyticsProperty(),
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
            activityEventSender.sendEvent(ActivityEvent.AppRateViewShowRequest)
        }
    }

    private fun onCalendarPreviewSubscribeClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_PREVIEW_SUBSCRIBE_TAP,
                )
            )
            sendViewCommand(JourneyCalendarViewCommand.NavigateTo(AppScreen.PaywallScreen))
        }
    }

    private fun onWatchVideoClicked() {
        if (adHelper.isLoading(AdHelper.AdType.Rewarded)) return
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_WATCH_VIDEO_TAP,
                )
            )
            activityEventSender
                .resultFlow
                .filterIsInstance<OnAdEventResult.AdShown>()
                .onEach { eventResult ->
                    (viewState as? JourneyCalendarViewState.Preview)?.let { currentState ->
                        setViewState(currentState.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Default))
                    }
                    if (eventResult.hasReward) {
                        journeyCalendarHelper.rewardHelper.onRewarded()
                        setData()
                    }
                }.launchIn(viewModelScope)
            adHelper.requestAdShow(
                adType = AdHelper.AdType.Rewarded,
                forceShow = true,
            )
            val currentState = viewState as? JourneyCalendarViewState.Preview ?: return@launch
            setViewState(currentState.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Loading))
        }
    }

    private suspend fun getCalendarAccessType(): JourneyCalendarViewState.CalendarAccessType {
        return when {
            getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription -> JourneyCalendarViewState.CalendarAccessType.Permanent
            journeyCalendarHelper.rewardHelper.hasAccessToCalendar() -> JourneyCalendarViewState.CalendarAccessType.UntilTheDayEnd
            else -> JourneyCalendarViewState.CalendarAccessType.NoAccess
        }
    }
}