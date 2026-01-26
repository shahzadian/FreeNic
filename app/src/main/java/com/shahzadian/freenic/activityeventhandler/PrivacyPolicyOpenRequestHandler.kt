package com.shahzadian.freenic.activityeventhandler

import com.freenic.activityevent.ActivityEvent
import com.freenic.ui.extensions.lazyUnsafe
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

private const val PRIVACY_POLICY_URL = "https://freenic.tech/privacy"
private const val TERMS_OF_SERVICE_URL = "https://freenic.tech/terms"
private const val SUBSCRIPTION_TERMS_URL = "https://freenic.tech/subscription"

internal class PrivacyPolicyOpenRequestHandler(
    coroutineScope: CoroutineScope,
) : ActivityEventHandler<ActivityEvent.PrivacyPolicyOpenRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.PrivacyPolicyOpenRequest> by lazyUnsafe {
        ActivityEvent.PrivacyPolicyOpenRequest::class
    }

    override fun onEvent(event: ActivityEvent.PrivacyPolicyOpenRequest) {
        sendCommand(ActivityCommand.OpenUrl(PRIVACY_POLICY_URL))
    }
}

internal class TermsOfServiceOpenRequestHandler(
    coroutineScope: CoroutineScope,
) : ActivityEventHandler<ActivityEvent.TermsOfServicesOpenRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.TermsOfServicesOpenRequest> by lazyUnsafe {
        ActivityEvent.TermsOfServicesOpenRequest::class
    }

    override fun onEvent(event: ActivityEvent.TermsOfServicesOpenRequest) {
        sendCommand(ActivityCommand.OpenUrl(TERMS_OF_SERVICE_URL))
    }
}

internal class SubscriptionTermsOpenRequestHandler(
    coroutineScope: CoroutineScope,
) : ActivityEventHandler<ActivityEvent.SubscriptionTermsOpenRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.SubscriptionTermsOpenRequest> by lazyUnsafe {
        ActivityEvent.SubscriptionTermsOpenRequest::class
    }

    override fun onEvent(event: ActivityEvent.SubscriptionTermsOpenRequest) {
        sendCommand(ActivityCommand.OpenUrl(SUBSCRIPTION_TERMS_URL))
    }
}