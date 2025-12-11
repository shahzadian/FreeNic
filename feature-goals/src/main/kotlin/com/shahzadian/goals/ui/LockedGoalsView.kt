package com.shahzadian.goals.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.goals.R

@Composable
fun LockedGoalsView(
    onUpgradeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(
            width = 0.2.dp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            HeaderView(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .alpha(0.3f)
            )
            PremiumWarningView(
                onUpgradeClicked = onUpgradeClicked,
            )
        }
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_goals_locked),
                contentDescription = "locked goals",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_locked_view_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun PremiumWarningView(
    onUpgradeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val upgradeClick: () -> Unit = remember {
        { onUpgradeClicked() }
    }
    Column(
        modifier = modifier
            .padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_premium_feature_lock),
                contentDescription = "premium lock",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_locked_premium_warning),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.goals_locked_view_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        MainSubmitButton(
            text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.goals_locked_view_button),
            onClick = upgradeClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
    }
}