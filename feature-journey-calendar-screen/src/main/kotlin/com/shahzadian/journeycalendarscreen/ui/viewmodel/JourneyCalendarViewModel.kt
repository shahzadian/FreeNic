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
import com.shahzadian.ad.reward.RewardHelper
import com.shahzadian.ad.reward.RewardHelperFactory
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayCell
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarHelper
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class JourneyCalendarViewModel @Inject constructor(
    private val journeyCalendarHelper: JourneyCalendarHelper,
    private val analyticsEventSender: AnalyticsEventSender,
    private val activityEventSender: ActivityEventSender,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val adHelper: AdHelper,
    private val rewardHelperFactory: RewardHelperFactory,
) : BaseViewModel<JourneyCalendarViewState, JourneyCalendarViewEvent, JourneyCalendarViewCommand>() {

    private val rewardHelper: RewardHelper by lazyUnsafe {
        rewardHelperFactory.create(
            params = RewardHelper.Params(
                featureKey = AppScreen.JourneyCalendarScreen.route,
                duration = RewardHelper.RewardDuration.Day,
            )
        )
    }

    override val initState: JourneyCalendarViewState by lazyUnsafe {
        JourneyCalendarViewState.Loading
    }

    private var tappetDay: JourneyCalendarDayCell? = null

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
            val hasRewardedAccess = rewardHelper.isRewardActual()
            if (hasPaidSubscription || hasRewardedAccess) {
                val model = journeyCalendarHelper.getMonth()
                setViewState(
                    state = JourneyCalendarViewState.Data(
                        calendarAccessType = getCalendarAccessType(),
                        hasPreviousMonth = journeyCalendarHelper.hasPreviousMonth(),
                        tappedDay = tappetDay,
                        journeyCalendarMonthData = model,
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

    private fun onDayTapped(journeyCalendarDayCell: JourneyCalendarDayCell) {
        tappetDay = journeyCalendarDayCell
        val currentState = viewState as? JourneyCalendarViewState.Data ?: return
        viewModelScope.launch {
            setViewState(
                state = currentState.copy(tappedDay = journeyCalendarDayCell)
            )
            val dayType = when {
                journeyCalendarDayCell.isToday -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_TODAY
                journeyCalendarDayCell.isInFuture -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_FUTURE
                else -> AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_VALUE_DAY_TYPE_PAST
            }
            analyticsEventSender.send(
                event = AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP, params = mapOf(
                        AnalyticsEventConst.JOURNEY_CALENDAR_DAY_TAP_PARAM_HAS_EVENTS to journeyCalendarDayCell.events.isNotEmpty()
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
            sendViewCommand(
                JourneyCalendarViewCommand.NavigateToPaywallScreen(
                    source = AppScreen.JourneyCalendarScreen.getBaseRoute()
                )
            )
        }
    }

    private fun onWatchVideoClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.JOURNEY_CALENDAR_WATCH_VIDEO_TAP,
                )
            )

            // Set loading state
            val currentState = viewState as? JourneyCalendarViewState.Preview ?: return@launch
            setViewState(currentState.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Loading))

            var isAdHandled = false

            // Listen for ad callbacks (both success and error)
            val callbackJob = launch {
                activityEventSender.resultFlow.collect { result ->
                    when (result) {
                        is OnAdEventResult.AdLoading -> {
                            // Ad started showing, cancel timeout
                            if (isAdHandled) return@collect
                            isAdHandled = true
                        }
                        is OnAdEventResult.AdShown -> {
                            (viewState as? JourneyCalendarViewState.Preview)?.let { state ->
                                setViewState(state.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Default))
                            }
                            if (result.hasReward) {
                                rewardHelper.onRewarded()
                                setData()
                            }
                        }
                        is OnAdEventResult.Error -> {
                            if (isAdHandled) return@collect

                            (viewState as? JourneyCalendarViewState.Preview)?.let { state ->
                                setViewState(state.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Default))
                            }
                            sendViewCommand(
                                JourneyCalendarViewCommand.ShowError(
                                    message = com.freenic.ui.wrapper.TextWrapper.FromRes(
                                        resId = com.shahzadian.baseui.R.string.ad_load_error
                                    )
                                )
                            )
                        }
                    }
                }
            }

            // Request ad to show
            adHelper.requestAdShow(
                adType = AdHelper.AdType.Rewarded,
                forceShow = true,
            )

            // Start timeout timer (5 seconds for ad loading only)
            launch {
                kotlinx.coroutines.delay(5000)
                if (!isAdHandled) {
                    isAdHandled = true
                    callbackJob.cancel()

                    // Cancel ad loading in MainActivity
                    activityEventSender.sendEvent(
                        com.freenic.activityevent.ActivityEvent.AdLoadingCancel
                    )

                    (viewState as? JourneyCalendarViewState.Preview)?.let { state ->
                        setViewState(state.copy(previewRewardState = JourneyCalendarViewState.PreviewRewardState.Default))
                    }
                    sendViewCommand(
                        JourneyCalendarViewCommand.ShowError(
                            message = com.freenic.ui.wrapper.TextWrapper.FromRes(
                                resId = com.shahzadian.baseui.R.string.ad_load_timeout
                            )
                        )
                    )
                }
            }
        }
    }

    private suspend fun getCalendarAccessType(): JourneyCalendarViewState.CalendarAccessType {
        return when {
            getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription -> JourneyCalendarViewState.CalendarAccessType.Permanent
            rewardHelper.isRewardActual() -> JourneyCalendarViewState.CalendarAccessType.UntilTheDayEnd
            else -> JourneyCalendarViewState.CalendarAccessType.NoAccess
        }
    }
}