package com.shahzadian.motivationalnotesscreen.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.AppLoaderView
import com.freenic.ui.extensions.HeaderScreen
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
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.motivational_notes_header),
        hasBackIcon = true,
        onBackIconClicked = onBackIconClick,
        modifier = modifier,
    ) {
        NoteView(
//            TODO
            data = data.notes.first(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
