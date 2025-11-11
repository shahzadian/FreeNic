package com.shahzadian.freenic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shahzadian.freenic.navigation.Navigation
import com.freenic.ui.theme.FreeNicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeNicTheme {
                Navigation()
            }
        }
    }
}