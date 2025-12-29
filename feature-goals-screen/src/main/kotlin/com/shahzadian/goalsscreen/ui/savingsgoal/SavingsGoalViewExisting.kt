package com.shahzadian.goalsscreen.ui.savingsgoal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainAnimatableLinearProgressIndicator
import com.shahzadian.goalsscreen.R
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState

@Composable
internal fun SavingsGoalViewExisting(
    savingsGoal: GoalsViewState.SavingsGoalUiModel,
    onDetailsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detailsClick = remember {
        { onDetailsClicked() }
    }
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        HeaderView(
            savingsGoal = savingsGoal,
            modifier = Modifier
                .padding(bottom = 8.dp),
        )
        ProgressView(
            percents = savingsGoal.achievedPercents,
            currentAmount = savingsGoal.currentAmount,
            necessaryAmount = savingsGoal.necessaryAmount,
            currency = savingsGoal.currency,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        DetailsView(
            noCigDays = savingsGoal.noCigDays,
            onDetailsClicked = detailsClick,
        )
    }
}

@Composable
private fun HeaderView(
    savingsGoal: GoalsViewState.SavingsGoalUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_saving_goal),
                contentDescription = "Savings Goal",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(30.dp)
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.goals_savings_goal),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            text = savingsGoal.name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun ProgressView(
    percents: Int,
    currentAmount: Float,
    necessaryAmount: Float,
    currency: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            val displayableCurrency = currency ?: ""
            Text(
                text = stringResource(
                    com.shahzadian.baseui.R.string.goals_savings_goal_progress_label,
                    percents
                ),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "$currentAmount$displayableCurrency / $necessaryAmount$displayableCurrency",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false,
                        )
                    ),
                )
            }
        }
        MainAnimatableLinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = percents.toFloat(),
        )
    }
}

@Composable
private fun DetailsView(
    noCigDays: Int,
    onDetailsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detailsClick = remember {
        { onDetailsClicked() }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SmokeFreeDaysView(
            noCigDays = noCigDays,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.goals_savings_goal_details),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable {
                    detailsClick()
                }
        )
    }
}

@Composable
private fun SmokeFreeDaysView(
    noCigDays: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_goals_savings_details_calendar),
            contentDescription = "Smoke Free Days",
            modifier = Modifier.padding(end = 4.dp)
        )
        val pluralText = pluralStringResource(
            com.shahzadian.baseui.R.plurals.goals_savings_goal_calendar,
            noCigDays,
            noCigDays
        )
        Text(
            text = pluralText,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
