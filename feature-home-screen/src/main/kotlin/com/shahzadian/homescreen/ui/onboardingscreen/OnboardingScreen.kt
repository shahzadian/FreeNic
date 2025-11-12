package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = viewModel(),
) {
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is OnboardingViewState.Data -> {
            val dataState = state as OnboardingViewState.Data
            OnboardingView(
                title = dataState.title,
                description = dataState.description,
                imageWrapper = dataState.imageWrapper,
                buttonText = dataState.buttonText,
                onButtonClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        else -> {}
    }
}
