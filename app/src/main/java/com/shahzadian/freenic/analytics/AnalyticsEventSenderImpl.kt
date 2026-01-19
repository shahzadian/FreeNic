package com.shahzadian.freenic.analytics

import android.content.Context
import android.os.Bundle
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst.PARAM_APP_BUILD_NUMBER
import com.freenic.analytics.AnalyticsEventConst.PARAM_APP_VERSION
import com.freenic.analytics.AnalyticsEventConst.PARAM_SUBSCRIPTION_ID
import com.freenic.analytics.AnalyticsEventConst.PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.AnalyticsProperty
import com.freenic.logger.AppLogger
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.google.firebase.analytics.FirebaseAnalytics
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
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
        params: Map<String, AnalyticsProperty<*>>,
    )

    protected suspend fun composeBaseParams(): Map<String, AnalyticsProperty<*>> {
        val currentSubscription =
            (getUserCurrentSubscriptionUseCase() as? UserSubscriptionStatus.HasSubscription)?.plan?.id
                ?: PARAM_VALUE_SUBSCRIPTION_ID_NO_SUBSCRIPTION
        val (appVersion, buildNumber) = appInfoHelper.getAppVersion().let {
            it.versionName to it.buildNumber
        }
        return mapOf(
            PARAM_APP_VERSION to AnalyticsProperty.StringAnalyticsProperty(appVersion),
            PARAM_APP_BUILD_NUMBER to AnalyticsProperty.IntAnalyticsProperty(buildNumber),
            PARAM_SUBSCRIPTION_ID to AnalyticsProperty.StringAnalyticsProperty(currentSubscription)
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
        params: Map<String, AnalyticsProperty<*>>
    ) {
        val bundle = Bundle()
            .apply {
                params.forEach { (key, value) ->
                    value.putIntoBundle(key, this)
                }
            }
        firebaseAnalytics.logEvent(eventName, bundle)
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

    override fun sendEvent(eventName: String, params: Map<String, AnalyticsProperty<*>>) {
        appLogger.logDebug("analytics", "Event: ${eventName}, params: $params")
    }
}
