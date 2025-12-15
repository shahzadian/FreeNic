package com.shahzadian.goalsscreen.ui.achievementsharescreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewCommand
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewModel
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewState
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
fun AchievementShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: AchievementShareViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is AchievementShareViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is AchievementShareViewState.Data -> {
            ContentView(
                data = (state as AchievementShareViewState.Data),
                modifier = modifier,
            )
        }
    }
    val context = LocalContext.current
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is AchievementShareViewCommand.ShowError -> {
                        Toast.makeText(
                            context,
                            context.textFromWrapper(command.message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is AchievementShareViewCommand.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: AchievementShareViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        when (data.achievementToShare.item) {
            is AchievementUiModel.AchievementItemUiModel.Days -> {
                DayBasedMinimalView(
                    achievementUiModel = data.achievementToShare,
                    isSelected = true,
                    modifier = modifier.fillMaxWidth(),
                )
            }

            is AchievementUiModel.AchievementItemUiModel.Cigs -> {
                TODO()
            }
        }
    }
}

private fun Context.shareImageByUri(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, null))
}