package com.shahzadian.goalsscreen.ui.savinggoal

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.goalsscreen.R

@Composable
fun SavingGoalViewLocked(
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
        PremiumWarningView(
            onUpgradeClicked = onUpgradeClicked,
            modifier = Modifier.padding(16.dp)
        )
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
                painter = painterResource(R.drawable.ic_saving_goal_premium),
                contentDescription = "premium lock",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_locked_view_title),
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
            color = MaterialTheme.colorScheme.onSecondary,
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