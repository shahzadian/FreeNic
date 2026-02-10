package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.activityevent.ActivityEventSender
import com.freenic.activityevent.OnAdEventResult
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.ad.AdHelper
import com.shahzadian.ad.reward.RewardHelper
import com.shahzadian.ad.reward.RewardHelperFactory
import com.shahzadian.insights.domain.GetInsightChartsBlocksUseCase
import com.shahzadian.insightsscreen.ui.InsightChartMapper
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InsightsViewModel @Inject constructor(
    private val getInsightChartsBlocksUseCase: GetInsightChartsBlocksUseCase,
    private val insightChartMapper: InsightChartMapper,
    private val getCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val adHelper: AdHelper,
    private val activityEventSender: ActivityEventSender,
    private val rewardHelperFactory: RewardHelperFactory,
) : BaseViewModel<InsightsViewState, InsightsViewEvent, InsightsViewCommand>() {

    override val initState: InsightsViewState by lazy { InsightsViewState.Loading }

    private val rewardHelper: RewardHelper by lazyUnsafe {
        rewardHelperFactory.create(
            params = RewardHelper.Params(
                featureKey = AppScreen.InsightsScreen.route,
                duration = RewardHelper.RewardDuration.Day,
            )
        )
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: InsightsViewEvent) {
        when (event) {
            is InsightsViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }

            is InsightsViewEvent.OnSubscribeClicked -> {
                onSubscribeClicked()
            }

            is InsightsViewEvent.OnWatchVideoClicked -> {
                onWatchVideoClicked()
            }

            is InsightsViewEvent.OnLogSlipClicked -> {
                onLogSlipClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val hasPaidSubscription =
                getCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription
            val hasRewardAccess = rewardHelper.isRewardActual()
            if (hasPaidSubscription || hasRewardAccess) {
                val chartBlocks = getInsightChartsBlocksUseCase()
                    .map { insightChartMapper.map(it) }
                if (chartBlocks.isNotEmpty()) {
                    setViewState(
                        InsightsViewState.Data(
                            chartBlocks = chartBlocks,
                            selectedBlockId = chartBlocks.first().id,
                            accessType = if (hasPaidSubscription) {
                                InsightsViewState.InsightsAccessType.Permanent
                            } else {
                                InsightsViewState.InsightsAccessType.UntilTheDayEnd
                            }
                        )
                    )
                }
            } else {
                setViewState(
                    InsightsViewState.Preview(
                        previewRewardState = InsightsViewState.PreviewRewardState.Default,
                    )
                )
            }
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(InsightsViewCommand.NavigateBack)
        }
    }

    private fun onSubscribeClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.INSIGHTS_SUBSCRIBE_TAP
                )
            )
            sendViewCommand(
                InsightsViewCommand.NavigateToPaywallScreen(
                    source = AppScreen.InsightsScreen.getBaseRoute()
                )
            )
        }
    }

    private fun onWatchVideoClicked() {
        if (adHelper.isLoading(AdHelper.AdType.Rewarded)) return
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.INSIGHTS_WATCH_VIDEO_TAP,
                )
            )

            // Set loading state
            val currentState = viewState as? InsightsViewState.Preview ?: return@launch
            setViewState(currentState.copy(previewRewardState = InsightsViewState.PreviewRewardState.Loading))

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
                            (viewState as? InsightsViewState.Preview)?.let { state ->
                                setViewState(state.copy(previewRewardState = InsightsViewState.PreviewRewardState.Default))
                            }
                            if (result.hasReward) {
                                rewardHelper.onRewarded()
                            }
                            setData()
                        }
                        is OnAdEventResult.Error -> {
                            if (isAdHandled) return@collect

                            (viewState as? InsightsViewState.Preview)?.let { state ->
                                setViewState(state.copy(previewRewardState = InsightsViewState.PreviewRewardState.Default))
                            }
                            sendViewCommand(
                                InsightsViewCommand.ShowError(
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

                    (viewState as? InsightsViewState.Preview)?.let { state ->
                        setViewState(state.copy(previewRewardState = InsightsViewState.PreviewRewardState.Default))
                    }
                    sendViewCommand(
                        InsightsViewCommand.ShowError(
                            message = com.freenic.ui.wrapper.TextWrapper.FromRes(
                                resId = com.shahzadian.baseui.R.string.ad_load_timeout
                            )
                        )
                    )
                }
            }
        }
    }

    private fun onLogSlipClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.INSIGHTS_LOG_SLIP_TAP
                )
            )
            sendViewCommand(
                InsightsViewCommand.NavigateTo(AppScreen.SlipConfirmationScreen)
            )
        }
    }
}
