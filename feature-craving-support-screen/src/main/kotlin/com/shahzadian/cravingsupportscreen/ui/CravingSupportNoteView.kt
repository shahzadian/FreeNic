package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainHintBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper

@Composable
internal fun CravingSupportNoteView(
    animationDuration: Int,
    animationDelay: Int,
    note: TextWrapper,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = animationDuration,
                delayMillis = animationDelay
            )
        ),
        modifier = modifier
    ) {
        NoteView(
            note = note,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NoteView(
    note: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .mainHintBackground()
            .padding(16.dp),
    ) {
        Text(
            text = textFromWrapper(note),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
