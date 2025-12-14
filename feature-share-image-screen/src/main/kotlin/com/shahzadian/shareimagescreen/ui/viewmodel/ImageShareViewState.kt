package com.shahzadian.shareimagescreen.ui.viewmodel

import com.freenic.ui.vm.ViewState
import com.shahzadian.shareimagescreen.domain.ImageToShare

internal sealed interface ImageShareViewState : ViewState {

    data object Loading : ImageShareViewState

    data class Data(
        val imageToShare: ImageToShare,
    ) : ImageShareViewState
}
