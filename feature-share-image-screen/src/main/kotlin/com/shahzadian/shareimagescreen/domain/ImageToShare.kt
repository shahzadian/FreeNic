package com.shahzadian.shareimagescreen.domain

import androidx.compose.runtime.Composable

sealed interface ImageToShare {

    class ComposableContent(
        val content: @Composable () -> Unit
    ) : ImageToShare
}
