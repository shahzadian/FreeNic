package com.shahzadian.freenic.activityeventhandler

import android.app.Activity
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCase
import com.shahzadian.subscription.domain.SubscribeToPremiumUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal interface ActivityEventHandlerFactory {

    fun create(
        activity: Activity,
        coroutineScope: CoroutineScope,
    ): List<ActivityEventHandler<*>>
}

internal class ActivityEventHandlerFactoryImpl @Inject constructor(
    private val subscribeToPremiumUseCase: SubscribeToPremiumUseCase,
    private val refreshSubscriptionStatusUseCase: RefreshSubscriptionStatusUseCase,
) : ActivityEventHandlerFactory {

    override fun create(
        activity: Activity,
        coroutineScope: CoroutineScope
    ): List<ActivityEventHandler<*>> {
        return listOf(
            AppSubscriptionRequestEventHandler(
                activity = activity,
                coroutineScope = coroutineScope,
                subscribeToPremiumUseCase = subscribeToPremiumUseCase,
                refreshSubscriptionStatusUseCase = refreshSubscriptionStatusUseCase,
            )
        )
    }
}