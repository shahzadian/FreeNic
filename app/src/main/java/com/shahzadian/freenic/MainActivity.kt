package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shahzadian.freenic.navigation.Navigation
import com.freenic.ui.theme.FreeNicTheme
import com.shahzadian.datafill.startscreen.PersonalDataFillStartScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeNicTheme {
               // Navigation()
                PersonalDataFillStartScreen()
            }
        }
    }
}
