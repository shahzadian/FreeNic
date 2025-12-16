package com.shahzadian.goalsscreen.ui.achievementsharescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.Success
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun DayBasedMinimalView(
    achievementUiModel: AchievementUiModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val achievementItem = achievementUiModel.item as? AchievementUiModel.AchievementItemUiModel.Days
        ?: throw IllegalArgumentException("Achievement item is not of type Days")
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isSelected) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        } else {
            null
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.background,
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DaysCountCircleView(
                    item = achievementItem,
                )
                TextView(
                    daysCount = achievementItem.count,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun DaysCountCircleView(
    item: AchievementUiModel.AchievementItemUiModel.Days,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .size(80.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Success,
                        MaterialTheme.colorScheme.onPrimaryContainer,
                        MaterialTheme.colorScheme.primary,
                    )
                ),
                shape = CircleShape,
            )
    ) {
        Text(
            text = item.count.toString(),
            color = Color.Black,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun TextView(
    daysCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = pluralStringResource(R.plurals.achievement_share_minimal_title, daysCount),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.achievement_share_minimal_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        ShareAchievementAppLogo(
            backgroundColor = null
        )
    }
}
