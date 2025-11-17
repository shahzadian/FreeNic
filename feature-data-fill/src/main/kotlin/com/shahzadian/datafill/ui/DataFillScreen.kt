package com.shahzadian.datafill.ui

import androidx.compose.foundation.background
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainGradientBackground
import com.freenic.ui.theme.Smoke
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.pages.DataFillAboutPage
import com.shahzadian.datafill.ui.pages.DataFillInitPage
import com.shahzadian.datafill.ui.pages.DataFillCigarettesPerDayPagePage

@Composable
fun DataFillScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: DataFillViewModel = hiltViewModel()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(mainGradientBackground())
    ) {
        val state by viewModel.viewStateFlow.collectAsState()

        when (state) {
            is DataFillViewState.Loading -> {}
            is DataFillViewState.Init -> {
                DataFillInitPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            }

            is DataFillViewState.About -> {
                DataFillAboutPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    data = state as DataFillViewState.About,
                    onNameChanged = { viewModel.onViewEvent(DataFillViewEvent.OnNameChanged(it)) },
                    onAgeChanged = { viewModel.onViewEvent(DataFillViewEvent.OnAgeChanged(it)) },
                )
            }

            is DataFillViewState.CigarettesPerDay -> {
                DataFillCigarettesPerDayPagePage(
                    data = state as DataFillViewState.CigarettesPerDay,
                    onCigarettesCountChanged = { viewModel.onViewEvent(DataFillViewEvent.OnCigarettesPerDayChanged(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            }
        }
        state.pagesData?.let { pagesData ->
            Footer(
                pagesCount = pagesData.pagesCount,
                currentPageIndex = pagesData.currentPageIndex,
                onSubmitClick = {
                    viewModel.onViewEvent(DataFillViewEvent.OnSubmitNext)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
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
            R.string.data_fill_submit_button
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
            val target = if (item <= currentPageIndex) 1f else 0f
            val progress = animateFloatAsState(
                targetValue = target,
                animationSpec = tween(durationMillis = 450),
                label = "page_indicator_progress"
            )

            Box(
                modifier = Modifier
                    .height(6.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.outline)
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.value)
                        .height(6.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}