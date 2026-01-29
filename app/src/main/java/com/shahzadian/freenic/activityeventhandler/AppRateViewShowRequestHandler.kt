package com.shahzadian.freenic.activityeventhandler

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.freenic.activityevent.ActivityEvent
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.appinfo.appdatacache.AppDataCacheUpdater
import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.apprate.domain.ShouldShowAppRateViewUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal class AppRateViewShowRequestHandler(
    @param:ApplicationContext
    private val context: Context,
    coroutineScope: CoroutineScope,
    private val shouldShowAppRateViewUseCase: ShouldShowAppRateViewUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val appDataCacheUpdater: AppDataCacheUpdater,
) : ActivityEventHandler<ActivityEvent.AppRateViewShowRequest>(
    coroutineScope = coroutineScope,
) {

    override val eventClass: KClass<ActivityEvent.AppRateViewShowRequest> by lazyUnsafe {
        ActivityEvent.AppRateViewShowRequest::class
    }

    override fun onEvent(event: ActivityEvent.AppRateViewShowRequest) {
        launchInScope {
            if (shouldShowAppRateViewUseCase()) {
                sendCommand(
                    ActivityCommand.ShowAppRateView(
                        onAppRated = {
                            onAppRated(it)
                        },
                        onNotNowClicked = {
                            onNotNowClicked()
                        },
                    )
                )
            }
        }
    }

    private fun onAppRated(starsCount: Int) {
        launchInScope {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.APP_RATE_CONFIRMED,
                    params = mapOf(
                        AnalyticsEventConst.APP_RATE_PARAM_STARS_COUNT to starsCount.asAnalyticsProperty()
                    )
                )
            )
            appDataCacheUpdater.onAppRated()
            openAppInGooglePlay()
        }
    }

    private fun onNotNowClicked() {
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.APP_RATE_NOT_NOW_TAP
            )
        )
    }

    private fun openAppInGooglePlay() {
        val appPackageName = context.packageName

        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
