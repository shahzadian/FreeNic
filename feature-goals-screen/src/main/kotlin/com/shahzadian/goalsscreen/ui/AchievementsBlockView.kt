package com.shahzadian.goalsscreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementsData

@Composable
internal fun AchievementsBlockView(
    data: AchievementsData,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAchievementShareClicked: (id: String) -> Unit = {},
) {
    val achievementShareClick: (id: String) -> Unit =
        remember { { onAchievementShareClicked(it) } }
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.goals_achievements_block_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Column(
            modifier = Modifier.padding(
                bottom = if (data.shouldShowMoreAchievementsButton) 16.dp else 0.dp
            )
        ) {
            val items = data.achievements
            val itemsPerRowRaw = data.achievementsPerRow
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
                            AchievementView(
                                achievement = achievement,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                onAchievementShareClicked = {
                                    achievementShareClick(achievement.id)
                                }
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
        if (data.shouldShowMoreAchievementsButton) {
            Text(
                text = stringResource(R.string.achievement_show_more),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = onMoreClick,
                    )
            )
        }
    }
}