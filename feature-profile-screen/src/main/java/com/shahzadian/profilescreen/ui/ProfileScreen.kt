package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .verticalScroll(scrollState)
    ) {
        ProfileHeaderView(
            userNameLetter = data.userNameLetter,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        ProfilePersonalDataBlockView(
            data = data.personalData,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProfileSmokingDetailsBlockView(
            data = data.smokingDetails,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        AppVersionTextView(
            appVersion = data.appVersion,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun AppVersionTextView(
    appVersion: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = appVersion,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall
    )
}
