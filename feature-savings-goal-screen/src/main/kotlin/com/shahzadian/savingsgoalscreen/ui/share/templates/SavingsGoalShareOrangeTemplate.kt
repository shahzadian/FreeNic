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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.ui.SavingsGoalDataUiModel
import com.shahzadian.savingsgoalscreen.ui.share.SavingsGoalShareCard

@Composable
internal fun SavingsGoalShareOrangeTemplate(
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
                Color(android.graphics.Color.parseColor("#FFD180")),
                Color(android.graphics.Color.parseColor("#F59E0B")),
                MaterialTheme.colorScheme.tertiary,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppIcon(
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.savings_goal_share_orange_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        Box {
            DataView(
                dataUiModel = dataUiModel,
            )
            Image(
                painter = imageFromWrapper(dataUiModel.category.icon),
                contentDescription = "Category",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .alpha(0.5f),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.tertiary,
                )
            )
        }
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
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.small,
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.savings_goal_share_orange_data_title),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = dataUiModel.name,
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        DataRowView(
            iconRes = com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_share_orange_data_money,
            title = stringResource(R.string.savings_goal_share_orange_data_money),
            data = dataUiModel.currentAmount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        DataRowView(
            iconRes = com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_share_orange_data_percents,
            title = stringResource(R.string.savings_goal_share_orange_data_progress),
            data = "${dataUiModel.achievedPercents}%",
        )
    }
}

@Composable
private fun DataRowView(
    iconRes: Int,
    title: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = title,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            textAlign = TextAlign.Start,
        )
        Text(
            text = data,
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}