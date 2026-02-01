package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CravingSupportActivityView(
    activities: ImmutableList<CravingSupportViewState.CravingSupportActivityUiModel>,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_activity_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .padding(bottom = 16.dp)
        )
        CarouselView(
            activities = activities,
            onItemClicked = onClicked,
            modifier = modifier,
        )
    }
}

@Composable
private fun CarouselView(
    activities: ImmutableList<CravingSupportViewState.CravingSupportActivityUiModel>,
    onItemClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val initialPageIndex = 0
    val pagerState = rememberPagerState(initialPage = initialPageIndex) {
        activities.size
    }
    Column(
        modifier = modifier
            .clickable {
                onItemClicked(activities[pagerState.currentPage].id)
            },
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.Top,
            pageSpacing = 8.dp
        ) { index ->
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val item = activities[index]
                ItemView(
                    uiModel = item,
                    onClicked = {
                        onItemClicked(item.id)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(activities.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (index == pagerState.currentPage) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

@Composable
private fun ItemView(
    uiModel: CravingSupportViewState.CravingSupportActivityUiModel,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val click = remember { { onClicked() } }
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        HeaderView(
            uiModel = uiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.craving_support_activity_submit_button),
            onClick = click,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderView(
    uiModel: CravingSupportViewState.CravingSupportActivityUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            painter = imageFromWrapper(uiModel.icon),
            contentDescription = "Header image",
            modifier = Modifier
                .size(50.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = textFromWrapper(uiModel.name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = textFromWrapper(uiModel.description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}
