package com.shahzadian.freenic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.freenic.CommonConst
import com.freenic.activityevent.ActivityEventReceiver
import com.freenic.activityevent.OnAdEventResult
import com.freenic.logger.AppLogger
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.ad.AdHelper
import com.shahzadian.apprate.ui.AppRateBottomSheet
import com.shahzadian.freenic.activityeventhandler.ActivityCommand
import com.shahzadian.freenic.activityeventhandler.ActivityEventHandlerFactory
import com.shahzadian.freenic.navigation.AppNavigation
import com.shahzadian.freenic.navigation.AppNavigatorFactory
import com.shahzadian.push.ui.PushShowDialog
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.RefreshSubscriptionStatusUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import com.shahzadian.subscription.domain.UserSubscriptionStatusChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
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

    private val appRateShowCommandFlow: MutableSharedFlow<ActivityCommand.ShowAppRateView> by lazyUnsafe {
        MutableSharedFlow()
    }

    private val pushPermissionRequestCommandFlow: MutableSharedFlow<ActivityCommand.PushPermissionRequestCommand> by lazyUnsafe {
        MutableSharedFlow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val targetScreen =
            intent.getStringExtra(CommonConst.KEY_INTENT_TARGET_SCREEN)
        setContent {
            FreeNicTheme {
                AppNavigation(
                    targetScreen = targetScreen,
                    appNavigatorFactory = appNavigatorFactory,
                )
                AppRateView()
                PushPermissionView()
            }
        }
        initializeAdSdk()
        subscribeToEvents()
    }

    @Composable
    private fun AppRateView() {
        var appRateCommand: ActivityCommand.ShowAppRateView? by remember { mutableStateOf(null) }
        LaunchedEffect(Unit) {
            appRateShowCommandFlow
                .collect {
                    appRateCommand = it
                }
        }
        appRateCommand?.let { command ->
            AppRateBottomSheet(
                onDismissRequest = { appRateCommand = null },
                onRated = {
                    command.onAppRated(it)
                    appRateCommand = null
                },
                onNotNowClicked = {
                    command.onNotNowClicked()
                    appRateCommand = null
                }
            )
        }
    }

    @Composable
    private fun PushPermissionView() {
        var pushRequestCommand: ActivityCommand.PushPermissionRequestCommand? by remember {
            mutableStateOf(
                null
            )
        }
        LaunchedEffect(Unit) {
            pushPermissionRequestCommandFlow
                .collect {
                    pushRequestCommand = it
                }
        }
        pushRequestCommand?.let { command ->
            var shouldLaunch by remember { mutableStateOf(false) }
            if (shouldLaunch) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        command.onPermissionGranted()
                    } else {
                        command.onPermissionDenied()
                    }
                    pushRequestCommand = null
                }
                SideEffect {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            PushShowDialog(
                isSoftAsk = showRationale,
                onSubmitClicked = {
                    if (showRationale) {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", packageName, null)
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        startActivity(intent)
                    } else {
                        shouldLaunch = true
                    }
                },
                onCancelClicked = {
                    command.onCancelClicked()
                }
            )
        }
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
            loadAd(AdHelper.AdType.Interstitial)
            loadAd(AdHelper.AdType.Rewarded)
        }
        adHelper
            .adShowRequestFlow
            .flatMapConcat { type ->
                adHelper.showAd(this@MainActivity, type)
            }
            .onEach { result ->
                when (result) {
                    is AdHelper.BannerAdShowResult.Error -> {
                        loadAd(
                            adType = result.adType,
                        )
                        activityEventReceiver.sendResult(
                            OnAdEventResult.Error(result.throwable)
                        )
                    }

                    is AdHelper.BannerAdShowResult.Dismissed -> {
                        loadAd(
                            adType = result.adType,
                        )
                    }

                    is AdHelper.BannerAdShowResult.Showing -> {}

                    is AdHelper.BannerAdShowResult.Rewarded -> {
                        activityEventReceiver.sendResult(
                            OnAdEventResult.AdShown(
                                hasReward = true,
                            )
                        )
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun loadAd(adType: AdHelper.AdType) {
        lifecycleScope.launch(coroutineContexts.main) {
            adHelper.loadBannerAd(adType).collect {}
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

        activityEventHandlers
            .map {
                it.resultFlow
            }
            .merge()
            .onEach { result ->
                activityEventReceiver.sendResult(result)
            }
            .launchIn(lifecycleScope)

        activityEventHandlers
            .map {
                it.activityCommandFlow
            }
            .merge()
            .onEach { command ->
                onActivityCommand(command)
            }
            .launchIn(lifecycleScope)

    }

    private fun onActivityCommand(command: ActivityCommand) {
        when (command) {
            is ActivityCommand.ShowAppRateView -> {
                lifecycleScope.launch {
                    appRateShowCommandFlow.emit(command)
                }
            }

            is ActivityCommand.OpenUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(command.url))
                startActivity(intent)
            }

            is ActivityCommand.PushPermissionRequestCommand -> {
                lifecycleScope.launch {
                    pushPermissionRequestCommandFlow.emit(command)
                }
            }
        }
    }

    private companion object {
        const val PUSH_PERMISSION_REQUEST_CODE = 1
    }
}
