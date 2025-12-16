package com.shahzadian.goalsscreen.ui.achievementsharescreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.achievementsharescreen.daybased.DayBasedMotivationalView
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewCommand
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewModel
import com.shahzadian.goalsscreen.ui.achievementsharescreen.viewmodel.AchievementShareViewState
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel
import java.io.File
import java.io.FileOutputStream

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
    var shouldRecord by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        AchievementShareContentView(
            data = data,
            shouldRecord = shouldRecord,
            onImageShared = {
                shouldRecord = false
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.ic_achievement_share_submit_button),
            onClick = {
                shouldRecord = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun AchievementShareContentView(
    data: AchievementShareViewState.Data,
    shouldRecord: Boolean,
    onImageShared: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val onBitmapRecord = remember {
        { bitmap: Bitmap ->
            val uri = bitmapToUri(context, bitmap)
            context.shareImageByUri(uri)
            onImageShared()
        }
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        when (data.achievementToShare.item) {
            is AchievementUiModel.AchievementItemUiModel.Days -> {
                val minimalViewTag = "minimalViewTag"
                val celebratoryViewTag = "celebratoryViewTag"
                val motivationalViewTag = "motivationalViewTag"
                var selectedItemTag: String by remember { mutableStateOf(minimalViewTag) }
                DayBasedMinimalView(
                    achievementUiModel = data.achievementToShare,
                    isSelected = selectedItemTag == minimalViewTag,
                    shouldRecord = shouldRecord && selectedItemTag == minimalViewTag,
                    onClick = { selectedItemTag = minimalViewTag },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onRecorded = onBitmapRecord
                )
                DayBasedCelebratoryView(
                    achievementUiModel = data.achievementToShare,
                    isSelected = selectedItemTag == celebratoryViewTag,
                    shouldRecord = shouldRecord && selectedItemTag == celebratoryViewTag,
                    onClick = { selectedItemTag = celebratoryViewTag },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onRecorded = onBitmapRecord
                )
                DayBasedMotivationalView(
                    achievementUiModel = data.achievementToShare,
                    isSelected = selectedItemTag == motivationalViewTag,
                    shouldRecord = shouldRecord && selectedItemTag == motivationalViewTag,
                    onClick = { selectedItemTag = motivationalViewTag },
                    modifier = Modifier
                        .fillMaxWidth(),
                    onRecorded = onBitmapRecord
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

private fun bitmapToUri(
    context: Context,
    bitmap: Bitmap,
): Uri {
    val cacheDir = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File(cacheDir, "shared_image.png")

    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}
