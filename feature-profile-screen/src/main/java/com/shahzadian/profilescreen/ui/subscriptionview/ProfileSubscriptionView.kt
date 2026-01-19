package com.shahzadian.profilescreen.ui.subscriptionview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahzadian.profilescreen.ui.viewmodel.ProfileViewState

@Composable
internal fun ProfileSubscriptionView(
    state: ProfileViewState.Data.SubscriptionState,
    onPremiumClicked: () -> Unit,
    onManageSubscriptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is ProfileViewState.Data.SubscriptionState.NoSubscription -> {
            NoSubscriptionView(
                onPremiumClicked = onPremiumClicked,
                modifier = modifier,
            )
        }

        is ProfileViewState.Data.SubscriptionState.HasSubscription -> {
            HasSubscriptionView(
                activeUntilDate = state.activeUntil,
                onManageSubscriptionClicked = onManageSubscriptionClicked,
                modifier = modifier,
            )
        }
    }
}
