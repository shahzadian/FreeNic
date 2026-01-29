package com.freenic.activityevent

import com.freenic.ui.extensions.lazyUnsafe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

interface ActivityEventSender {

    val resultFlow: Flow<ActivityEventResult>

    suspend fun sendEvent(event: ActivityEvent)
}

interface ActivityEventReceiver {

    val eventFlow: Flow<ActivityEvent>

    suspend fun sendResult(result: ActivityEventResult)
}

sealed interface ActivityEvent {

    data class AppSubscriptionRequest(
        val planId: String,
    ) : ActivityEvent

    data object AppRateViewShowRequest : ActivityEvent

    data object PrivacyPolicyOpenRequest : ActivityEvent

    data object TermsOfServicesOpenRequest : ActivityEvent

    data object SubscriptionTermsOpenRequest : ActivityEvent

    data object PushPermissionShowRequest : ActivityEvent
}

interface ActivityEventResult

data class OnPushPermissionResult(
    val isGranted: Boolean,
) : ActivityEventResult

sealed interface OnAdEventResult : ActivityEventResult {

    data class Error(
        val throwable: Throwable,
    ) : OnAdEventResult

    data class AdShown(
        val hasReward: Boolean,
    ) : OnAdEventResult
}

@Singleton
internal class ActivityEventHelper @Inject constructor() : ActivityEventSender,
    ActivityEventReceiver {

    override val resultFlow: MutableSharedFlow<ActivityEventResult> by lazyUnsafe {
        MutableSharedFlow()
    }

    override val eventFlow: MutableSharedFlow<ActivityEvent> by lazyUnsafe {
        MutableSharedFlow()
    }

    override suspend fun sendEvent(event: ActivityEvent) {
        eventFlow.emit(event)
    }

    override suspend fun sendResult(result: ActivityEventResult) {
        resultFlow.emit(result)
    }
}