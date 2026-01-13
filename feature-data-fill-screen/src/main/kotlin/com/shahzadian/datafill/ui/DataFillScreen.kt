package com.shahzadian.datafill.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.theme.Smoke
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.ui.pages.DataFillAboutPage
import com.shahzadian.datafill.ui.pages.DataFillCigarettesPerDayPagePage
import com.shahzadian.datafill.ui.pages.DataFillInitPage
import com.shahzadian.datafill.ui.pages.DataFillPackPricePage
import com.shahzadian.datafill.ui.pages.result.DataFillResultPage

@Composable
fun DataFillScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: DataFillViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ScreenByState(
            state = state,
            onViewEvent = viewModel::onViewEvent,
        )

        state.pagesData?.let { pagesData ->
            Footer(
                pagesCount = pagesData.pagesCount,
                currentPageIndex = pagesData.currentPageIndex,
                submitButtonEnabled = state.submitButtonEnabled,
                onSubmitClick = {
                    viewModel.onViewEvent(DataFillViewEvent.OnSubmitNext)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
    BackHandler(
        enabled = state.pagesData?.currentPageIndex?.let { it > 0 } ?: false,
    ) {
        viewModel.onViewEvent(DataFillViewEvent.OnBack)
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is DataFillCommand.NavigateTo -> {
                        navController.navigate(command.screen.route)
                    }
                }
            }
    }
}

@Composable
private fun ScreenByState(
    state: DataFillViewState,
    onViewEvent: (DataFillViewEvent) -> Unit,
) {
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
                data = state,
                onNameChanged = { onViewEvent(DataFillViewEvent.OnNameChanged(it)) },
                onAgeChanged = { onViewEvent(DataFillViewEvent.OnAgeChanged(it)) },
            )
        }

        is DataFillViewState.CigarettesPerDay -> {
            DataFillCigarettesPerDayPagePage(
                data = state,
                onCigarettesCountChanged = {
                    onViewEvent(
                        DataFillViewEvent.OnCigarettesPerDayChanged(
                            it
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }

        is DataFillViewState.PackPrice -> {
            DataFillPackPricePage(
                data = state,
                onPriceChanged = { price ->
                    onViewEvent(
                        DataFillViewEvent.OnPackPriceChanged(
                            price = price,
                        )
                    )
                },
                onQuitDateChanged = {
                    onViewEvent(
                        DataFillViewEvent.OnQuitDateChanged(
                            quitDateMillis = it,
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }

        is DataFillViewState.Result -> {
            DataFillResultPage(
                userData = state.userData,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }
    }
}

@Composable
private fun Footer(
    pagesCount: Int,
    currentPageIndex: Int,
    submitButtonEnabled: Boolean,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Smoke.copy(alpha = 0.3f))
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 48.dp),
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
            enabled = submitButtonEnabled,
            onClick = onSubmitClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SubmitButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MainSubmitButton(
        text = TextWrapper.FromRes(
            R.string.data_fill_submit_button
        ),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
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