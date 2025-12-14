package com.shahzadian.shareimagescreen.ui.viewmodel

import android.net.Uri
import com.freenic.ui.vm.ViewCommand

internal sealed interface ImageShareViewCommand : ViewCommand {

    data object NavigateBack : ImageShareViewCommand

    data class ShareImageByUri(
        val uri: Uri
    ) : ImageShareViewCommand
}
