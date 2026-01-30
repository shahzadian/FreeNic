package com.shahzadian.goalsscreen.ui.savingsgoal

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
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainSubmitButton
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.goalsscreen.R

@Composable
fun SavingsGoalViewNew(
    onCreateButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onCreateButtonClick = remember { { onCreateButtonClicked() } }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = mainCardBackground
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            HeaderView(
                modifier = Modifier.padding(bottom = 16.dp)
            )
            SubmitButtonView(
                onCreateButtonClicked = onCreateButtonClick,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            FooterInfoView(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_saving_goal),
            contentDescription = "header",
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_savings_goal_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_savings_goal_subtitle),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SubmitButtonView(
    onCreateButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val onButtonClick = remember { { onCreateButtonClicked() } }
    MainSubmitButton(
        text = TextWrapper.FromRes(com.shahzadian.baseui.R.string.goals_savings_goal_create_button),
        onClick = onButtonClick,
        modifier = modifier,
    )
}

@Composable
private fun FooterInfoView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_saving_goal_info_premium),
            contentDescription = "info premium",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.goals_savings_goal_premium_info),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
