package com.shahzadian.datafill.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainGradientBackground
import com.freenic.ui.theme.Smoke
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun DataFillScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradientBackground())
    ) {
        Footer(
            pagesCount = 5,
            currentPageIndex = 1,
            onSubmitClick = { },
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun Footer(
    pagesCount: Int,
    currentPageIndex: Int,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Smoke.copy(alpha = 0.3f))
            .padding(24.dp)
    ) {
        PagesIndicators(
            pagesCount = pagesCount,
            currentPageIndex = currentPageIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .padding(bottom = 24.dp)
        )
        SubmitButton(
            onClick = onSubmitClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MainSubmitButton(
        text = TextWrapper.FromRes(
            R.string.personal_data_fill_main_screen_submit_button
        ),
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun PagesIndicators(
    pagesCount: Int,
    currentPageIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pagesCount) { item ->
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        if (item <= currentPageIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                    .weight(1f)
            )
        }
    }
}