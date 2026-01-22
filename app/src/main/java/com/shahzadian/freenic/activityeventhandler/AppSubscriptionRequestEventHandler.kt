package com.shahzadian.freenic.activityeventhandler

import android.app.Activity
import com.freenic.activityevent.ActivityEvent
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCase
import com.shahzadian.subscription.domain.SubscribeToPremiumUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatusChanged
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class AppSubscriptionRequestEventHandler(
    private val activity: Activity,
    coroutineScope: CoroutineScope,
    private val subscribeToPremiumUseCase: SubscribeToPremiumUseCase,
    private val refreshSubscriptionStatusUseCase: RefreshSubscriptionStatusUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : ActivityEventHandler<ActivityEvent.AppSubscriptionRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.AppSubscriptionRequest> by lazyUnsafe {
        ActivityEvent.AppSubscriptionRequest::class
    }

    override fun onEvent(event: ActivityEvent.AppSubscriptionRequest) {
        launchInScope {
            val isSubscribed = subscribeToPremiumUseCase(activity, event.planId)
            if (isSubscribed) {
                val status = refreshSubscriptionStatusUseCase()
                analyticsEventSender.send(
                    AnalyticsEvent(
                        name = AnalyticsEventConst.SUBSCRIPTION_SUCCESS,
                        params = mapOf(
                            AnalyticsEventConst.SUBSCRIPTION_SUCCESS_PLAN_ID to event.planId.asAnalyticsProperty(),
                        )
                    )
                )
                sendResult(
                    UserSubscriptionStatusChanged(
                        status = status,
                    )
                )
            }
        }
    }
}
