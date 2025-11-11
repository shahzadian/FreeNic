package com.shahzadian.homescreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.shahzadian.homescreen.ui.welcomescreen.WelcomeScreen

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    WelcomeScreen(
        modifier = Modifier.fillMaxSize()
    )
}
