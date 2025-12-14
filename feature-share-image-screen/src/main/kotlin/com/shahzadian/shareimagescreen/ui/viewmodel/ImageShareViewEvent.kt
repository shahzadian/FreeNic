package com.shahzadian.shareimagescreen.ui.viewmodel

import android.graphics.Bitmap
import com.freenic.ui.vm.ViewEvent

internal sealed interface ImageShareViewEvent : ViewEvent {

    data class OnBitmapShareRequired(val bitmap: Bitmap) : ImageShareViewEvent

    data object OnBitmapShareOpened : ImageShareViewEvent
}
