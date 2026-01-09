package com.shahzadian.subscription.paywall.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewState

@Composable
internal fun SubscriptionPlanView(
    plan: PaywallViewState.SubscriptionPlanUiModel,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardClick = remember(plan) {
        { onClick() }
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = mainCardBackground,
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 0.2.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
            }
        ),
        onClick = cardClick,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = textFromWrapper(plan.title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                plan.savingPercents?.let { savePercents ->
                    SaveMoneyView(
                        savePercents = savePercents,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${plan.currency}${plan.totalPrice}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = textFromWrapper(plan.priceDetails),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}

@Composable
private fun SaveMoneyView(
    savePercents: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.subscription_plan_saving_percents, savePercents),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.error,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    )
}
