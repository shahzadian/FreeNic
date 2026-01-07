package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.freenic.AppLaunchConst
import com.freenic.ui.model.CoroutineContexts
import com.freenic.ui.theme.FreeNicTheme
import com.google.android.gms.ads.MobileAds
import com.shahzadian.freenic.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coroutineContexts: CoroutineContexts

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

    private fun initializeAdSdk() {
        lifecycleScope.launch(coroutineContexts.io) {
            MobileAds.initialize(this@MainActivity)
        }
    }
}
