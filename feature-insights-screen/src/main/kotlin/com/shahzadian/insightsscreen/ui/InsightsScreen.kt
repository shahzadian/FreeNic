package com.shahzadian.insightsscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun InsightsScreen(
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.insights_header_title),
        subtitle = TextWrapper.FromRes(R.string.insights_header_subtitle),
        icon = ImageWrapper.FromRes(com.shahzadian.insightsscreen.R.drawable.ic_insights_header),
        modifier = modifier,
    ) { }
}
