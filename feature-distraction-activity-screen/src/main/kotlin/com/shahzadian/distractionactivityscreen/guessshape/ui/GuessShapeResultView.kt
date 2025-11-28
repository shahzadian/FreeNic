package com.shahzadian.distractionactivityscreen.guessshape.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
internal fun GuessShapeResultView(
    didUserWin: Boolean,
    onRestartClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onRestartClick: () -> Unit = remember { { onRestartClicked() } }
    val onCloseClick: () -> Unit = remember { { onCloseClicked() } }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderView(
            didUserWin = didUserWin,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            MainSubmitButton(
                text = TextWrapper.FromRes(R.string.guess_shape_result_restart),
                onClick = onRestartClick,
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
            MainSubmitButton(
                text = TextWrapper.FromRes(R.string.guess_shape_result_close),
                onClick = onCloseClick,
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                containerColor = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun HeaderView(
    didUserWin: Boolean,
    modifier: Modifier = Modifier,
) {
    val text = if (didUserWin) {
        R.string.guess_shape_result_correct
    } else {
        R.string.guess_shape_result_incorrect
    }
    Text(
        text = stringResource(text),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
        textAlign = TextAlign.Center,
    )
}