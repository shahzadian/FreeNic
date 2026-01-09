package com.shahzadian.motivationalnotesscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.MainToolbarView
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.motivationalnotesscreen.ui.viewmodel.MotivationalNotesViewModel
import com.shahzadian.motivationalnotesscreen.ui.viewmodel.MotivationalNotesViewState

@Composable
fun MotivationalNotesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val onBackIconClicked: () -> Unit = remember {
        { navController.popBackStack() }
    }
    val viewModel: MotivationalNotesViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is MotivationalNotesViewState.Loading -> {
            AppLoaderView(
                modifier = modifier.fillMaxSize()
            )
        }

        is MotivationalNotesViewState.Data -> {
            ContentView(
                data = (state as MotivationalNotesViewState.Data),
                onBackIconClicked = onBackIconClicked,
                modifier = modifier.fillMaxSize()
            )
        }
    }

}

@Composable
private fun ContentView(
    data: MotivationalNotesViewState.Data,
    onBackIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onBackIconClick = remember {
        { onBackIconClicked() }
    }
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        MainToolbarView(
            title = TextWrapper.FromRes(R.string.motivational_notes_header),
            hasBackIcon = true,
            onBackIconClicked = onBackIconClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 16.dp),
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NotesPagerView(
                data = data,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            SwipeHintView(

            )
        }
    }
}

@Composable
private fun NotesPagerView(
    data: MotivationalNotesViewState.Data,
    modifier: Modifier = Modifier,
) {
    val actualCount = data.notes.size
    val infiniteCount = Int.MAX_VALUE
    val initialPageIndex = 0
    val pagerState = rememberPagerState(initialPage = initialPageIndex) {
        infiniteCount
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        pageSpacing = 8.dp
    ) { page ->
        val actualIndex = page % actualCount
        NoteView(
            data = data.notes[actualIndex],
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
