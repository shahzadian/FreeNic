package com.shahzadian.savinggoalscreen.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun SavingGoalScreen(
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.saving_goal_header_title),
        modifier = modifier
    ) {

    }
}
