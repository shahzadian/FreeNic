package com.shahzadian.goalsscreen.ui.achievementsscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.AppLoaderView
import com.shahzadian.goalsscreen.ui.AchievementsBlockView
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewModel
import com.shahzadian.goalsscreen.ui.achievementsscreen.vewmodel.AchievementsViewState

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: AchievementsViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is AchievementsViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is AchievementsViewState.Data -> {
            ContentView(
                data = (state as AchievementsViewState.Data),
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ContentView(
    data: AchievementsViewState.Data,
    modifier: Modifier = Modifier,
) {
    AchievementsBlockView(
        data = data.achievementsData,
        onMoreClick = {},
        modifier = modifier.padding(16.dp),
    )
}
