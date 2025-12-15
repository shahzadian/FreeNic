package com.shahzadian.goalsscreen.ui.achievementsharescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun DayBasedMinimalView(
    achievementUiModel: AchievementUiModel,
    modifier: Modifier = Modifier,
) {
    val achievementItem = achievementUiModel.item as? AchievementUiModel.AchievementItemUiModel.Days
        ?: throw IllegalArgumentException("Achievement item is not of type Days")
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
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
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DaysCountCircleView(
                    item = achievementItem,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextView(
                    title = achievementUiModel.title,
                    subtitle = achievementUiModel.description,
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
    Text(
        text = item.count.toString(),
        color = Color.Black,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ),
            )
            .padding(8.dp)
    )
}

@Composable
private fun TextView(
    title: TextWrapper,
    subtitle: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = textFromWrapper(title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = textFromWrapper(subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
