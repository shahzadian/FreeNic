package com.shahzadian.goalsscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.goals_header_title),
        subtitle = TextWrapper.FromRes(R.string.goals_header_subtitle),
        modifier = modifier,
        icon = ImageWrapper.FromRes(com.shahzadian.goalsscreen.R.drawable.ic_goals_header)
    ) { }
}
