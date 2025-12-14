package com.shahzadian.goalsscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState

@Composable
internal fun AchievementsBlockView(
    data: GoalsViewState.Data,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.goals_achievements_block_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Column {
            val items = data.achievementsData.achievements
            val itemsPerRowRaw = data.achievementsData.achievementsPerRow
            val itemsPerRow = if (itemsPerRowRaw > 0) itemsPerRowRaw else 1

            val rows = items.chunked(itemsPerRow)
            rows.forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (rowIndex == rows.lastIndex) 0.dp else 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { achievement ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val minHeight = 150
                            AchievementView(
                                achievement = achievement,
                                minHeight = minHeight,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(
                                        minHeight = minHeight.dp
                                    )
                            )
                        }
                    }
                    val placeholders = itemsPerRow - rowItems.size
                    if (placeholders > 0) {
                        repeat(placeholders) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        if (data.achievementsData.shouldShowMoreAchievementsButton) {
            Text(
                text = stringResource(R.string.achievement_show_more),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = onMoreClick,
                    )
                    .padding(top = 16.dp)
            )
        }
    }
}