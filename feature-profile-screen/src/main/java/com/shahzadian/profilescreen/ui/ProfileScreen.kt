package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewCommand
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewEvent
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewModel
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is ProfileViewState.Loading -> {}
        is ProfileViewState.Data -> {
            ContentView(
                data = (state as ProfileViewState.Data),
                onViewEvent = viewModel::onViewEvent,
                modifier = modifier
            )
        }
    }
    LaunchedEffect(viewModel.viewCommandFlow) {
        viewModel
            .viewCommandFlow
            .collect { command ->
                when (command) {
                    is ProfileViewCommand.NavigateTo -> {
                        navController.navigate(command.screen.route)
                    }
                }
            }
    }
}

@Composable
private fun ContentView(
    onViewEvent: (ProfileViewEvent) -> Unit,
    data: ProfileViewState.Data,
    modifier: Modifier = Modifier
) {
    val onEvent: (ProfileViewEvent) -> Unit = remember {
        { event: ProfileViewEvent ->
            onViewEvent(event)
        }
    }
    HeaderScreen(
        title = TextWrapper.FromRes(R.string.profile_header_title),
        subtitle = TextWrapper.FromRes(R.string.profile_header_subtitle),
        image = {
            Box(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
                    .size(40.dp)
            ) {
                Text(
                    text = data.userNameLetter,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    ) {
        ProfilePersonalDataBlockView(
            data = data.personalData,
            onViewEvent = onEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProfileSmokingDetailsBlockView(
            data = data.smokingDetails,
            onViewEvent = onEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        if (data.isPremiumActive.not()) {
            ProfileSubscriptionView(
                onPremiumClicked = {
                    onEvent(ProfileViewEvent.OnPremiumClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        AppVersionTextView(
            appVersion = data.appVersion,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
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
