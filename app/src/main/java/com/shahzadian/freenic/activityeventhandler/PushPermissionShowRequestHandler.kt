package com.shahzadian.freenic.activityeventhandler

import android.content.Context
import android.content.pm.PackageManager
import com.freenic.CommonConst
import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.OnPushPermissionResult
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.ui.extensions.lazyUnsafe
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class PushPermissionShowRequestHandler(
    coroutineScope: CoroutineScope,
    private val context: Context,
    private val analyticsEventSender: AnalyticsEventSender,
) : ActivityEventHandler<ActivityEvent.PushPermissionShowRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.PushPermissionShowRequest> by lazyUnsafe {
        ActivityEvent.PushPermissionShowRequest::class
    }

    private val prefs by lazyUnsafe {
        context.getSharedPreferences(
            CommonConst.PREF_NAME_APP_SETTINGS,
            Context.MODE_PRIVATE,
        )
    }

    override fun onEvent(event: ActivityEvent.PushPermissionShowRequest) {
        val lastShowMillis = prefs.getLong(PREF_KEY_PUSH_MILLIS, -1)
        if (lastShowMillis > 0 && System.currentTimeMillis() - lastShowMillis < 3 * 24 * 60 * 60 * 1000) return
        val pushPermission = android.Manifest.permission.POST_NOTIFICATIONS
        val check = context.checkSelfPermission(pushPermission)
        if (check == PackageManager.PERMISSION_GRANTED) return
        sendCommand(
            ActivityCommand.PushPermissionRequestCommand(
                onPermissionGranted = {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.PUSH_PERMISSION_GRANTED,
                        )
                    )
                    sendResult(
                        OnPushPermissionResult(
                            isGranted = true,
                        )
                    )
                },
                onPermissionDenied = {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.PUSH_PERMISSION_DENIED,
                        )
                    )
                    sendResult(
                        OnPushPermissionResult(
                            isGranted = false,
                        )
                    )
                },
                onCancelClicked = {
                    prefs.edit().putLong(PREF_KEY_PUSH_MILLIS, System.currentTimeMillis()).apply()
                }
            )
        )
    }

    private companion object {
        const val PREF_KEY_PUSH_MILLIS: String = "push_millis"
    }
}
