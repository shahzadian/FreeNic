package com.shahzadian.insightsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.insightsscreen.domain.GetInsightChartsBlocksUseCase
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
) : BaseViewModel<InsightsViewState, InsightsViewEvent, InsightsViewCommand>() {

    override val initState: InsightsViewState by lazy { InsightsViewState.Loading }

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
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val chartBlocks = getInsightChartsBlocksUseCase()
                .map { insightChartMapper.map(it) }
            val hasPaidSubscription =
                getCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription
            if (chartBlocks.isNotEmpty()) {
                setViewState(
                    InsightsViewState.Data(
                        hasPaidSubscription = hasPaidSubscription,
                        chartBlocks = chartBlocks,
                        selectedBlockId = chartBlocks.first().id
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
            sendViewCommand(InsightsViewCommand.NavigateTo(AppScreen.PaywallScreen))
        }
    }
}
