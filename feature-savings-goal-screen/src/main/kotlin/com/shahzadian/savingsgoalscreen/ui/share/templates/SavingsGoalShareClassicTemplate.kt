package com.shahzadian.savingsgoalscreen.ui.share.templates

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainAnimatableLinearProgressIndicator
import com.freenic.ui.extensions.imageFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel
import com.shahzadian.savingsgoalscreen.ui.share.SavingsGoalShareCard

@Composable
internal fun SavingsGoalShareClassicTemplate(
    dataUiModel: SavingsGoalDataUiModel,
    isSelected: Boolean,
    shouldRecord: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRecorded: (Bitmap) -> Unit = {},
) {
    SavingsGoalShareCard(
        isSelected = isSelected,
        shouldRecord = shouldRecord,
        onCardClick = onClick,
        containerColorBrush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.surface,
            ),
        ),
        modifier = modifier,
        onRecorded = onRecorded,
        content = {
            ContentView(
                dataUiModel = dataUiModel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
private fun ContentView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AppIcon(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp),
        )
        Text(
            text = stringResource(R.string.savings_goal_share_classic_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = stringResource(R.string.savings_goal_share_classic_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        DataView(
            dataUiModel = dataUiModel,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AppIcon(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 8.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun DataView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.small,
            )
            .padding(16.dp),
    ) {
        DataHeaderView(
            dataUiModel = dataUiModel,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ProgressView(
            dataUiModel = dataUiModel,
        )
    }
}

@Composable
private fun DataHeaderView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imageFromWrapper(dataUiModel.category.icon),
            contentDescription = "Category",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column {
            Text(
                text = dataUiModel.name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            val necessaryAmount = dataUiModel.necessaryAmount
            val displayableCurrency = dataUiModel.currency ?: ""
            Text(
                text = stringResource(
                    R.string.savings_goal_share_classic_data_header_subtitle,
                    "$necessaryAmount$displayableCurrency"
                ),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ProgressView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.savings_goal_share_classic_data_progress_label),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleMedium,
            )
            val savedMoney = dataUiModel.currentAmount
            val displayableCurrency = dataUiModel.currency ?: ""
            Text(
                text = "${savedMoney}$displayableCurrency",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        MainAnimatableLinearProgressIndicator(
            progress = dataUiModel.achievedPercents.toFloat(),
        )
    }
}
