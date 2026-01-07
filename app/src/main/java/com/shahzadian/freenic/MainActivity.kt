package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.freenic.AppLaunchConst
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.freenic.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
    }
}
