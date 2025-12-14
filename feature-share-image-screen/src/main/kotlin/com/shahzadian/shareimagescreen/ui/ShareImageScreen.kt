package com.shahzadian.shareimagescreen.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.shahzadian.shareimagescreen.domain.ImageToShare
import com.shahzadian.shareimagescreen.ui.viewmodel.ImageShareViewCommand
import com.shahzadian.shareimagescreen.ui.viewmodel.ImageShareViewEvent
import com.shahzadian.shareimagescreen.ui.viewmodel.ImageShareViewModel
import com.shahzadian.shareimagescreen.ui.viewmodel.ImageShareViewState
import kotlinx.coroutines.launch

@Composable
fun ShareImageScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: ImageShareViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    val context = LocalContext.current
    when (state) {
        is ImageShareViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is ImageShareViewState.Data -> {
            ContentView(
                data = (state as ImageShareViewState.Data),
                onViewEvent = viewModel::onViewEvent,
                modifier = modifier.fillMaxSize(),
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is ImageShareViewCommand.NavigateBack -> {
                        navController.popBackStack()
                    }

                    is ImageShareViewCommand.ShareImageByUri -> {
                        context.shareImageByUri(command.uri)
                        viewModel.onViewEvent(ImageShareViewEvent.OnBitmapShareOpened)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    data: ImageShareViewState.Data,
    onViewEvent: (ImageShareViewEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    val bitmapShareEvent: (Bitmap) -> Unit = remember {
        { onViewEvent(ImageShareViewEvent.OnBitmapShareRequired(it)) }
    }
    Box(
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .drawWithContent {
                // Record the content into the graphics layer
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                // Draw the recorded layer to the screen
                drawLayer(graphicsLayer)
            },
    ) {
        when (data.imageToShare) {
            is ImageToShare.ComposableContent -> {
                data.imageToShare.content()
            }
        }
    }
    coroutineScope.launch {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        bitmapShareEvent(bitmap)
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