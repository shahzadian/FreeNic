package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.freenic.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreeNicTheme {
                Navigation()
//                Scaffold {
//                    DashboardScreen(
//                        modifier = Modifier.background(mainGradientBackground())
//                    )
//                }
            }
        }
    }
}