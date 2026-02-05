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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.MainAnimatableLinearProgressIndicator
import com.freenic.ui.extensions.imageFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel
import com.shahzadian.savingsgoalscreen.ui.share.SavingsGoalShareCard

@Composable
internal fun SavingsGoalShareBlueTemplate(
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
                Color(android.graphics.Color.parseColor("#3BAFDA")),
                Color(android.graphics.Color.parseColor("#2563EB")),
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
        TopView(
            dataUiModel = dataUiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.savings_goal_share_blue_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        ProgressView(
            dataUiModel = dataUiModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InfoView(
                infoLabel = stringResource(R.string.savings_goal_share_blue_days_label),
                infoData = dataUiModel.noCigDays.toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )
            InfoView(
                infoLabel = stringResource(R.string.savings_goal_share_blue_saved_money_label),
                infoData = dataUiModel.currentAmount,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
private fun TopView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            painter = imageFromWrapper(dataUiModel.category.icon),
            contentDescription = "Category",
            modifier = Modifier.padding(end = 8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
        AppIcon()
    }
}

@Composable
private fun ProgressView(
    dataUiModel: SavingsGoalDataUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.savings_goal_share_blue_progress_goal_label),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = stringResource(R.string.savings_goal_share_blue_progress_label),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dataUiModel.name,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 4.dp),
            )
            Text(
                text = "${dataUiModel.achievedPercents}%",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        MainAnimatableLinearProgressIndicator(
            progress = dataUiModel.achievedPercents.toFloat()
        )
    }
}

@Composable
private fun InfoView(
    infoLabel: String,
    infoData: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        Text(
            text = infoLabel,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = infoData,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
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