package com.shahzadian.freenic.analytics

import android.content.Context
import android.os.Bundle
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEvent.EventParams.PARAM_APP_BUILD_NUMBER
import com.freenic.analytics.AnalyticsEvent.EventParams.PARAM_APP_VERSION
import com.freenic.analytics.AnalyticsEvent.EventParams.PARAM_SUBSCRIPTION_ID
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.logger.AppLogger
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.google.firebase.analytics.FirebaseAnalytics
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal abstract class BaseAnalyticsEventSender(
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val appInfoHelper: AppInfoHelper,
    private val coroutineContexts: CoroutineContexts,
) : AnalyticsEventSender {

    private val coroutineScope: CoroutineScope by lazyUnsafe {
        CoroutineScope(coroutineContexts.io)
    }

    final override fun send(event: AnalyticsEvent) {
        launchInScope {
            val params = composeBaseParams() + event.params
            sendEvent(
                eventName = event.name,
                params = params,
            )
        }
    }

    protected abstract fun sendEvent(
        eventName: String,
        params: Map<String, AnalyticsEvent.PropertyType<*>>,
    )

    protected suspend fun composeBaseParams(): Map<String, AnalyticsEvent.PropertyType<*>> {
        val currentSubscription = getUserCurrentSubscriptionUseCase()?.id
            ?: AnalyticsEvent.EventParams.PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION
        val (appVersion, buildNumber) = appInfoHelper.getAppVersion().let {
            it.versionName to it.buildNumber
        }
        return mapOf(
            PARAM_APP_VERSION to AnalyticsEvent.PropertyType.StringProperty(appVersion),
            PARAM_APP_BUILD_NUMBER to AnalyticsEvent.PropertyType.IntProperty(buildNumber),
            PARAM_SUBSCRIPTION_ID to AnalyticsEvent.PropertyType.StringProperty(currentSubscription)
        )
    }

    protected fun launchInScope(scope: suspend () -> Unit) {
        coroutineScope.launch { scope() }
    }
}

internal class AnalyticsEventSenderProd(
    private val context: Context,
    getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    appInfoHelper: AppInfoHelper,
    coroutineContexts: CoroutineContexts,
) : BaseAnalyticsEventSender(
    getUserCurrentSubscriptionUseCase = getUserCurrentSubscriptionUseCase,
    appInfoHelper = appInfoHelper,
    coroutineContexts = coroutineContexts,
) {

    private val firebaseAnalytics by lazyUnsafe {
        FirebaseAnalytics.getInstance(context)
    }

    override fun sendEvent(
        eventName: String,
        params: Map<String, AnalyticsEvent.PropertyType<*>>
    ) {
        val bundle = Bundle()
            .apply {
                params.forEach { (key, value) ->
                    value.putIntoBundle(key, this)
                }
            }
    }
}

internal class AnalyticsEventSenderDebug(
    private val appLogger: AppLogger,
    getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    appInfoHelper: AppInfoHelper,
    coroutineContexts: CoroutineContexts,
) : BaseAnalyticsEventSender(
    getUserCurrentSubscriptionUseCase = getUserCurrentSubscriptionUseCase,
    appInfoHelper = appInfoHelper,
    coroutineContexts = coroutineContexts,
) {

    override fun sendEvent(eventName: String, params: Map<String, AnalyticsEvent.PropertyType<*>>) {
        appLogger.logDebug("analytics", "Event: ${eventName}, params: $params")
    }
}
