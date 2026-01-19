package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.freenic.AppLaunchConst
import com.freenic.activityevent.ActivityEventReceiver
import com.freenic.logger.AppLogger
import com.freenic.ui.model.CoroutineContexts
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.ad.AdHelper
import com.shahzadian.freenic.activityeventhandler.ActivityEventHandlerFactory
import com.shahzadian.freenic.navigation.AppNavigation
import com.shahzadian.freenic.navigation.AppNavigatorFactory
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import com.shahzadian.subscription.domain.UserSubscriptionStatusChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coroutineContexts: CoroutineContexts

    @Inject
    lateinit var adHelper: AdHelper

    @Inject
    lateinit var getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase

    @Inject
    lateinit var appNavigatorFactory: AppNavigatorFactory

    @Inject
    lateinit var refreshSubscriptionStatusUseCase: RefreshSubscriptionStatusUseCase

    @Inject
    lateinit var appLogger: AppLogger

    @Inject
    lateinit var activityEventReceiver: ActivityEventReceiver

    @Inject
    lateinit var activityEventHandlerFactory: ActivityEventHandlerFactory

    private var latestSubscriptionStatus: UserSubscriptionStatus? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val targetScreen =
            intent.getStringExtra(AppLaunchConst.KEY_INTENT_TARGET_SCREEN)
        setContent {
            FreeNicTheme {
                AppNavigation(
                    targetScreen = targetScreen,
                    appNavigatorFactory = appNavigatorFactory,
                )
            }
        }
        initializeAdSdk()
        subscribeToEvents()
    }

    override fun onStart() {
        super.onStart()
        // Обновляем статус подписки из Billing SDK при каждом запуске на экран
        lifecycleScope.launch(coroutineContexts.io) {
            try {
                val subscriptionStatus = refreshSubscriptionStatusUseCase()
                if (latestSubscriptionStatus != subscriptionStatus) {
                    latestSubscriptionStatus = subscriptionStatus
                    activityEventReceiver.sendResult(
                        UserSubscriptionStatusChanged(
                            status = subscriptionStatus,
                        )
                    )
                }
            } catch (t: Throwable) {
                appLogger.logError(
                    tag = "BillingDataSource",
                    message = "Failed to refresh subscription status",
                    throwable = t,
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeAdSdk() {
        lifecycleScope.launch(coroutineContexts.main) {
            if (getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.HasSubscription) {
                return@launch
            }
            delay(1000)
            adHelper.initialize(this@MainActivity)
            loadAd()
        }
        adHelper
            .adShowRequestFlow
            .flatMapConcat {
                adHelper.showAd(this@MainActivity)
            }
            .onEach { result ->
                when (result) {
                    is AdHelper.BannerAdShowResult.Error -> {
                        loadAd()
                    }

                    is AdHelper.BannerAdShowResult.Dismissed -> {
                        loadAd()
                    }

                    is AdHelper.BannerAdShowResult.Showing -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun loadAd() {
        lifecycleScope.launch(coroutineContexts.main) {
            adHelper.loadBannerAd()
                .collect {
//                    Result is not needed
                }
        }
    }

    private fun subscribeToEvents() {
        val activityEventHandlers = activityEventHandlerFactory.create(
            activity = this,
            coroutineScope = lifecycleScope,
        )
        activityEventReceiver
            .eventFlow
            .onEach { event ->
                activityEventHandlers.forEach { it.handle(event) }
            }
            .launchIn(lifecycleScope)
    }
}
