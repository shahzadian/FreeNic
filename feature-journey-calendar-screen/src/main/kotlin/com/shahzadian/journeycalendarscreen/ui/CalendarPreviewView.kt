package com.shahzadian.journeycalendarscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.journeycalendarscreen.R
import kotlinx.coroutines.delay

@Composable
internal fun CalendarPreviewView(
    onSubscribeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subscribeClick = remember { { onSubscribeClicked() } }
    Box(
        modifier = modifier.padding(16.dp),
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            CarouselView(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.journey_calendar_preview_submit_button),
            onClick = subscribeClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
        PremiumBadge(
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_preview_header_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_preview_header_subtitle),
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
            painter = painterResource(com.shahzadian.baseui.R.drawable.ic_premium),
            contentDescription = "premium badge",
            modifier = Modifier
                .padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.journey_calendar_preview_header_premium),
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
        R.drawable.image_journey_calendar_preview_1,
        R.drawable.image_journey_calendar_preview_2,
        R.drawable.image_journey_calendar_preview_3,
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
            Image(
                painter = painterResource(id = images[actualIndex]),
                contentDescription = "image $actualIndex",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}