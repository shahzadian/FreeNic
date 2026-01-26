package com.shahzadian.freenic.activityeventhandler

import com.freenic.activityevent.ActivityEvent
import com.freenic.ui.extensions.lazyUnsafe
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

private const val PRIVACY_POLICY_URL = "https://freenic.tech/privacy"
private const val TERMS_OF_SERVICE_URL = "https://freenic.tech/terms"

internal class PrivacyPolicyShowRequestHandler(
    coroutineScope: CoroutineScope,
) : ActivityEventHandler<ActivityEvent.PrivacyPolicyViewShowRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.PrivacyPolicyViewShowRequest> by lazyUnsafe {
        ActivityEvent.PrivacyPolicyViewShowRequest::class
    }

    override fun onEvent(event: ActivityEvent.PrivacyPolicyViewShowRequest) {
        sendCommand(ActivityCommand.OpenUrl(PRIVACY_POLICY_URL))
    }
}

internal class TermsOfServiceShowRequestHandler(
    coroutineScope: CoroutineScope,
) : ActivityEventHandler<ActivityEvent.TermsOfServiceViewShowRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.TermsOfServiceViewShowRequest> by lazyUnsafe {
        ActivityEvent.TermsOfServiceViewShowRequest::class
    }

    override fun onEvent(event: ActivityEvent.TermsOfServiceViewShowRequest) {
        sendCommand(ActivityCommand.OpenUrl(TERMS_OF_SERVICE_URL))
    }
}