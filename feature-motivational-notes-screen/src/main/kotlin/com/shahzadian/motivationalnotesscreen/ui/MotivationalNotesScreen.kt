package com.shahzadian.motivationalnotesscreen.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Composable
fun MotivationalNotesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val onBackIconClicked: () -> Unit = remember {
        { navController.popBackStack() }
    }
    ContentView(
        onBackIconClicked = onBackIconClicked,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun ContentView(
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onBackIconClick = remember {
        { onBackIconClicked() }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.motivational_notes_header),
        hasBackIcon = true,
        onBackIconClicked = onBackIconClick,
        modifier = modifier,
    ) { }
}
