package com.shahzadian.savingsgoalscreen.ui.share

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun SavingsGoalShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backClick: () -> Unit = remember {
        { navController.popBackStack() }
    }
    ContentView(
        onBackIconClicked = backClick,
        modifier = modifier
            .fillMaxSize(),
    )
}

@Composable
private fun ContentView(
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backIconClick = remember {
        { onBackIconClicked() }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.savings_goal_share_title),
        modifier = modifier,
        subtitle = null,
        icon = null,
        hasBackIcon = true,
        onBackIconClicked = backIconClick,
    ) {

    }
}
