package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.ComposeConst
import com.freenic.ui.extensions.MainAnimatableLinearProgressIndicator
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.wrapper.ImageWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewState

@Composable
internal fun SavingsGoalDetailsCardView(
    dataUiModel: SavingsGoalDetailsViewState.SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        HeaderView(
            goalName = dataUiModel.name,
            necessaryAmount = dataUiModel.necessaryAmount,
            currency = dataUiModel.currency,
            categoryIcon = dataUiModel.category.icon,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ProgressView(
            percents = dataUiModel.achievedPercents,
        )
    }
}

@Composable
private fun HeaderView(
    goalName: String,
    necessaryAmount: Float,
    currency: String?,
    categoryIcon: ImageWrapper,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = goalName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            val displayableCurrency = currency ?: ""
            Text(
                text = stringResource(
                    R.string.savings_goal_details_card_goal,
                    "$necessaryAmount$displayableCurrency"
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
        Image(
            painter = imageFromWrapper(categoryIcon),
            contentDescription = "Goal category",
        )
    }
}

@Composable
private fun ProgressView(
    percents: Int,
    modifier: Modifier = Modifier,
) {
    val percentsAnimatable = remember { Animatable(percents.toFloat()) }
    LaunchedEffect(Unit) {
        percentsAnimatable.snapTo(0f)
        percentsAnimatable.animateTo(
            targetValue = percents.toFloat(),
            animationSpec = tween(
                durationMillis = ComposeConst.PROGRESS_ANIMATION_DURATION,
            )
        )
    }
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.savings_goal_details_card_progress),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "${percentsAnimatable.value.toInt()}%",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        MainAnimatableLinearProgressIndicator(
            progress = percents.toFloat(),
        )
    }
}
