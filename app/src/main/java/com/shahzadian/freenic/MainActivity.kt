package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.freenic.AppLaunchConst
import com.freenic.ui.model.CoroutineContexts
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.ad.AdHelper
import com.shahzadian.freenic.navigation.AppNavigation
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coroutineContexts: CoroutineContexts

    @Inject
    lateinit var adHelper: AdHelper

    @Inject
    lateinit var getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val targetScreen =
            intent.getStringExtra(AppLaunchConst.KEY_INTENT_TARGET_SCREEN)
        setContent {
            FreeNicTheme {
                AppNavigation(
                    targetScreen = targetScreen,
                )
            }
        }
        initializeAdSdk()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeAdSdk() {
        lifecycleScope.launch(coroutineContexts.main) {
            if (getUserCurrentSubscriptionUseCase() != null) {
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
}
