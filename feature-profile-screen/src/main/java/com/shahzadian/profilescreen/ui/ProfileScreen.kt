package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewModel
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is ProfileViewState.Loading -> {}
        is ProfileViewState.Data -> {
            ContentView(
                data = (state as ProfileViewState.Data),
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ContentView(
    data: ProfileViewState.Data,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        ProfileHeaderView(
            userNameLetter = data.userNameLetter,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
