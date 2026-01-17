package com.shahzadian.freenic.activityeventhandler

import android.app.Activity
import com.freenic.activityevent.ActivityEvent
import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.subscription.domain.SubscribeToPremiumUseCase
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class AppSubscriptionRequestEventHandler(
    private val activity: Activity,
    coroutineScope: CoroutineScope,
    private val subscribeToPremiumUseCase: SubscribeToPremiumUseCase,
) : ActivityEventHandler<ActivityEvent.AppSubscriptionRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.AppSubscriptionRequest> by lazyUnsafe {
        ActivityEvent.AppSubscriptionRequest::class
    }

    override fun onEvent(event: ActivityEvent.AppSubscriptionRequest) {
        launchInScope {
            val isSubscribed = subscribeToPremiumUseCase(activity, event.planId)
        }
    }
}