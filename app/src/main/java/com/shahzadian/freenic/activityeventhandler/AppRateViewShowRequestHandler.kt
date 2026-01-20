package com.shahzadian.freenic.activityeventhandler

import com.freenic.activityevent.ActivityEvent
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.apprate.domain.ShouldShowAppRateViewUseCase
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class AppRateViewShowRequestHandler(
    coroutineScope: CoroutineScope,
    private val shouldShowAppRateViewUseCase: ShouldShowAppRateViewUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : ActivityEventHandler<ActivityEvent.AppRateViewShowRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.AppRateViewShowRequest> by lazyUnsafe {
        ActivityEvent.AppRateViewShowRequest::class
    }

    override fun onEvent(event: ActivityEvent.AppRateViewShowRequest) {
        launchInScope {
            if (shouldShowAppRateViewUseCase()) {
                sendCommand(
                    ActivityCommand.ShowAppRateView(
                        onAppRated = {
                            onAppRated(it)
                        },
                        onNotNowClicked = {
                            onNotNowClicked()
                        },
                    )
                )
            }
        }
    }

    private fun onAppRated(starsCount: Int) {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.APP_RATE_CONFIRMED,
                params = mapOf(
                    AnalyticsEventConst.APP_RATE_PARAM_STARS_COUNT to starsCount.asAnalyticsProperty()
                )
            )
        )
    }

    private fun onNotNowClicked() {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.APP_RATE_NOT_NOW_TAP
            )
        )
    }
}