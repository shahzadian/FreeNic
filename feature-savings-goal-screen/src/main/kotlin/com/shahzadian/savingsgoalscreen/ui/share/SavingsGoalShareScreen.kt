package com.shahzadian.savingsgoalscreen.ui.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.share.templates.SavingsGoalShareClassicTemplate
import com.shahzadian.savingsgoalscreen.ui.share.templates.SavingsGoalShareOrangeTemplate
import com.shahzadian.savingsgoalscreen.ui.share.viewmodel.SavingsGoalShareViewModel
import com.shahzadian.savingsgoalscreen.ui.share.viewmodel.SavingsGoalShareViewState
import java.io.File
import java.io.FileOutputStream

@Composable
fun SavingsGoalShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backClick: () -> Unit = remember {
        { navController.popBackStack() }
    }
    val viewModel: SavingsGoalShareViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is SavingsGoalShareViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize(),
            )
        }

        is SavingsGoalShareViewState.Data -> {
            ContentView(
                data = (state as SavingsGoalShareViewState.Data),
                onBackIconClicked = backClick,
                modifier = modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
private fun ContentView(
    data: SavingsGoalShareViewState.Data,
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    var shouldRecord by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val onBitmapRecord = remember {
        { bitmap: Bitmap ->
            val uri =
                bitmapToUri(context, bitmap)
            context.shareImageByUri(uri)
            shouldRecord = false
        }
    }
    val orangeTemplateId = 0
    val classicTemplateId = 1
    var selectedTemplateId by remember { mutableStateOf(orangeTemplateId) }
    Box(
        modifier = modifier,
    ) {
        HeaderScreen(
            title = TextWrapper.FromRes(R.string.savings_goal_share_title),
            subtitle = null,
            icon = null,
            hasBackIcon = true,
            onBackIconClicked = backIconClick,
            modifier = Modifier.padding(bottom = 84.dp)
        ) {
            SavingsGoalShareOrangeTemplate(
                dataUiModel = data.dataUiModel,
                isSelected = selectedTemplateId == orangeTemplateId,
                shouldRecord = shouldRecord && selectedTemplateId == orangeTemplateId,
                onClick = {
                    selectedTemplateId = orangeTemplateId
                },
                onRecorded = onBitmapRecord,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )
            SavingsGoalShareClassicTemplate(
                dataUiModel = data.dataUiModel,
                isSelected = selectedTemplateId == classicTemplateId,
                shouldRecord = shouldRecord && selectedTemplateId == classicTemplateId,
                onClick = {
                    selectedTemplateId = classicTemplateId
                },
                onRecorded = onBitmapRecord,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        MainSubmitButton(
            text = TextWrapper.FromRes(R.string.savings_goal_share_submit_button),
            onClick = {
                shouldRecord = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
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
