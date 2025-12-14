package com.shahzadian.shareimagescreen.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.shareimagescreen.domain.GetUriToShareUseCase
import com.shahzadian.shareimagescreen.domain.ImageShareController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ImageShareViewModel @Inject constructor(
    private val imageShareController: ImageShareController,
    private val getUriToShareUseCase: GetUriToShareUseCase,
) : BaseViewModel<ImageShareViewState, ImageShareViewEvent, ImageShareViewCommand>() {

    override val initState: ImageShareViewState by lazyUnsafe { ImageShareViewState.Loading }

    init {
        setData()
    }

    override fun onViewEventHappened(event: ImageShareViewEvent) {
        when (event) {
            is ImageShareViewEvent.OnBitmapShareRequired -> {
                shareBitmap(event.bitmap)
            }

            is ImageShareViewEvent.OnBitmapShareOpened -> {
                viewModelScope.launch {
                    sendViewCommand(ImageShareViewCommand.NavigateBack)
                }
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val imageToShare = imageShareController.getCurrentImageToShare()
                ?: let {
                    sendViewCommand(ImageShareViewCommand.NavigateBack)
                    return@launch
                }
            setViewState(
                ImageShareViewState.Data(imageToShare)
            )
        }
    }

    private fun shareBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                val uri = getUriToShareUseCase(bitmap)
                sendViewCommand(ImageShareViewCommand.ShareImageByUri(uri))
            } finally {
                imageShareController.setImageToShare(null)
            }
        }
    }
}
