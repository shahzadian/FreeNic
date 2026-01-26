package com.shahzadian.freenic.activityeventhandler

import android.app.Activity
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.appinfo.appdatacache.AppDataCacheUpdater
import com.shahzadian.apprate.domain.ShouldShowAppRateViewUseCase
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
    private val shouldShowAppRateViewUseCase: ShouldShowAppRateViewUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val appDataCacheUpdater: AppDataCacheUpdater,
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
                analyticsEventSender = analyticsEventSender,
            ),
            AppRateViewShowRequestHandler(
                coroutineScope = coroutineScope,
                shouldShowAppRateViewUseCase = shouldShowAppRateViewUseCase,
                analyticsEventSender = analyticsEventSender,
                appDataCacheUpdater = appDataCacheUpdater,
            ),
            PrivacyPolicyShowRequestHandler(
                coroutineScope = coroutineScope,
            ),
            TermsOfServiceShowRequestHandler(
                coroutineScope = coroutineScope,
            ),
        )
    }
}
