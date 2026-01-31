package com.shahzadian.insightsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.screenWidth
import com.shahzadian.baseui.R
import com.shahzadian.insightsscreen.ui.viewmodel.InsightsViewState
import kotlinx.coroutines.delay

@Composable
internal fun InsightsPreviewView(
    rewardState: InsightsViewState.PreviewRewardState,
    onWatchVideoClicked: () -> Unit,
    onSubscribeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subscribeClick = remember { { onSubscribeClicked() } }
    val watchVideoClick = remember { { onWatchVideoClicked() } }
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            CarouselView(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        FooterView(
            rewardState = rewardState,
            onWatchVideoClicked = watchVideoClick,
            onSubscribeClicked = subscribeClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.insights_header_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.insights_header_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PremiumBadge(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_premium),
            contentDescription = "premium badge",
            modifier = Modifier
                .padding(end = 8.dp)
        )
        Text(
            text = stringResource(R.string.journey_calendar_preview_header_premium),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun CarouselView(
    modifier: Modifier = Modifier,
) {
    val images = listOf(
        com.shahzadian.insightsscreen.R.drawable.image_insights_preview_1,
        com.shahzadian.insightsscreen.R.drawable.image_insights_preview_2,
        com.shahzadian.insightsscreen.R.drawable.image_insights_preview_3,
    )
    val actualCount = images.size
    val infiniteCount = Int.MAX_VALUE
    val initialPageIndex = 0
    val pagerState = rememberPagerState(initialPage = initialPageIndex) {
        infiniteCount
    }
    var actualIndex = 0
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
            delay(1000)
        }
    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.Top,
            userScrollEnabled = false,
        ) { page ->
            actualIndex = page % actualCount
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = images[actualIndex]),
                    contentDescription = "image $actualIndex",
                    modifier = Modifier
                        .width(screenWidth.dp / 2)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center,
                )
            }
        }
    }
}

@Composable
private fun FooterView(
    rewardState: InsightsViewState.PreviewRewardState,
    onWatchVideoClicked: () -> Unit,
    onSubscribeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WatchVideoButton(
            rewardState = rewardState,
            onClicked = onWatchVideoClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_reward_info),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_preview_button_or),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        UpgradeButton(
            onClicked = onSubscribeClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        )
    }
}

@Composable
private fun WatchVideoButton(
    rewardState: InsightsViewState.PreviewRewardState,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClicked)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        when (rewardState) {
            is InsightsViewState.PreviewRewardState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            is InsightsViewState.PreviewRewardState.Default -> {
                Image(
                    painter = painterResource(com.shahzadian.insightsscreen.R.drawable.ic_calendar_video_button),
                    contentDescription = "video button",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_reward_submit),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}


@Composable
private fun UpgradeButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClicked)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(com.shahzadian.baseui.R.drawable.ic_premium),
            contentDescription = "upgrade",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_preview_submit_button),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}