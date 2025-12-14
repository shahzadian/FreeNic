package com.shahzadian.shareimagescreen.domain

import javax.inject.Inject

interface ImageShareController {

    fun setImageToShare(imageToShare: ImageToShare?)

    fun getCurrentImageToShare(): ImageToShare?
}

internal class ImageShareControllerImpl @Inject constructor() : ImageShareController {

    private var imageToShare: ImageToShare? = null

    override fun getCurrentImageToShare(): ImageToShare? {
        return imageToShare
    }

    override fun setImageToShare(imageToShare: ImageToShare?) {
        this.imageToShare = imageToShare
    }
}
