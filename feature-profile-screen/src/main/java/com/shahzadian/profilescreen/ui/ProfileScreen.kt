package com.shahzadian.profilescreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.navigation.AppNavigator
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.ui.subscriptionview.ProfileSubscriptionView
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewCommand
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewEvent
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewModel
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
fun ProfileScreen(
    navigator: AppNavigator,
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
                        navigator.navigateToScreen(command.screen) {
                            popUpTo(AppScreen.WelcomeScreen.route) { inclusive = true }
                        }
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
        ProfileSubscriptionView(
            state = data.subscriptionState,
            onPremiumClicked = {
                onEvent(ProfileViewEvent.OnUpgradeSubscriptionClicked)
            },
            onManageSubscriptionClicked = {
                onEvent(ProfileViewEvent.OnManageSubscriptionClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        ProfilePrivacyBlockView(
            onPrivacyClicked = {
                onEvent(ProfileViewEvent.OnPrivacyClicked)
            },
            onTermsClicked = {
                onEvent(ProfileViewEvent.OnTermsClicked)
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (data.isDebugBuild) {
            DebugBuildView(
                onDebugClicked = {
                    onEvent(ProfileViewEvent.OnDebugPanelOpenClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
            )
        }
        ProfileClearAllDataView(
            onClicked = {
                onEvent(ProfileViewEvent.OnClearAllDataConfirmed)
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.profile_support_email, data.supportEmail),
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = data.appVersion,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}

@Composable
private fun DebugBuildView(
    onDebugClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onDebugClicked,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(com.shahzadian.profilescreen.R.drawable.ic_profile_debug),
                contentDescription = "debug",
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = "Debug panel",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}