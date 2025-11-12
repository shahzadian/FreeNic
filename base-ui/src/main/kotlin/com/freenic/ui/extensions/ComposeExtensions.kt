package com.freenic.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper

@Composable
fun imageFromWrapper(wrapper: ImageWrapper): Painter {
    return when (wrapper) {
        is ImageWrapper.FromRes -> {
            painterResource(wrapper.resId)
        }
    }
}

@Composable
fun textFromWrapper(wrapper: TextWrapper): String {
    return when (wrapper) {
        is TextWrapper.FromRes -> {
            stringResource(
                id = wrapper.resId,
                formatArgs = wrapper.args.toTypedArray()
            )
        }

        is TextWrapper.FromString -> wrapper.text
    }
}
