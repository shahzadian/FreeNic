package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
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
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.details.viewmodel.SavingsGoalDetailsViewState

@Composable
internal fun SavingsGoalDetailsHeaderView(
    dataUiModel: SavingsGoalDetailsViewState.SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    val amountAnimatable = remember { Animatable(0f) }
    LaunchedEffect(dataUiModel.necessaryAmount) {
        amountAnimatable.animateTo(
            targetValue = dataUiModel.currentAmount,
            animationSpec = tween(durationMillis = 1500),
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.savings_goal_details_saved_so_far),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        val displayableCurrency = dataUiModel.currency ?: ""
        val displayableAmount = if (amountAnimatable.value == dataUiModel.currentAmount) {
            amountAnimatable.value
        } else {
            amountAnimatable.value.toInt().toFloat()
        }
        Text(
            text = "${displayableAmount}$displayableCurrency",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayMedium
        )
    }
}
