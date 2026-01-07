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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeAdSdk()
        val targetScreen =
            intent.getStringExtra(AppLaunchConst.KEY_INTENT_TARGET_SCREEN)
        setContent {
            FreeNicTheme {
                AppNavigation(
                    targetScreen = targetScreen,
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initializeAdSdk() {
        lifecycleScope.launch(coroutineContexts.main) {
            adHelper.initialize(this@MainActivity)
            adHelper
                .loadBannerAd()
                .collect {
//                    Result is not needed
                }
        }
        adHelper
            .adShowRequestFlow
            .flatMapConcat {
                adHelper.showAd(this@MainActivity)
            }
            .onEach {
//                Result is not needed
            }
            .launchIn(lifecycleScope)
    }
}
