package com.shahzadian.goalsscreen.ui.achievementsharescreen.moneybased

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.Smoke
import com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementCard
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun MoneyBasedMinimalView(
    achievementUiModel: AchievementUiModel,
    isSelected: Boolean,
    shouldRecord: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRecorded: (Bitmap) -> Unit = {},
) {
    ShareAchievementCard(
        isSelected = isSelected,
        shouldRecord = shouldRecord,
        containerBrush = Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.background,
                Smoke,
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.primaryContainer,
            )
        ),
        onCardClick = onClick,
        modifier = modifier,
        onRecorded = onRecorded,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(com.shahzadian.goalsscreen.R.drawable.ic_achievement_share_money_based_minimal),
                contentDescription = "Money based minimal",
                modifier = Modifier.padding(bottom = 16.dp),
            )
            val item = achievementUiModel.item as AchievementUiModel.AchievementItemUiModel.Money

            Text(
                text = item.displayableAmount(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.achievement_share_money_based_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.achievement_share_money_based_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Text(
                text = stringResource(com.shahzadian.baseui.R.string.app_name),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
