package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.freenic.ui.extensions.mainGradientBackground
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryUi
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreeNicTheme {
                //  Navigation()
                Box(
                    modifier = Modifier.fillMaxSize().background(mainGradientBackground()),
                    contentAlignment = Alignment.Center
                ) {
                    LungsRecoveryView(
                        model = LungsRecoveryUi(
                            recoveryPercentage = 70,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}