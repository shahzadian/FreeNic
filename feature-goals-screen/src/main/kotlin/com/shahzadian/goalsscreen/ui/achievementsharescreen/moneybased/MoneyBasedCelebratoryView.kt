package com.shahzadian.goalsscreen.ui.achievementsharescreen.moneybased

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.Success
import com.shahzadian.baseui.R
import com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementCard
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun MoneyBasedCelebratoryView(
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
            colors = listOf(
                Success,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.primary,
            )
        ),
        onCardClick = onClick,
        modifier = modifier,
        onRecorded = onRecorded,
    ) {
        TopStartEmoji(
            modifier = Modifier.align(Alignment.TopStart)
        )
        TopEndEmoji(
            modifier = Modifier.align(Alignment.TopEnd)
        )
        BottomStartEmoji(
            modifier = Modifier.align(Alignment.BottomStart)
        )
        BottomEndEmoji(
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconView(
                modifier = Modifier
                    .padding(bottom = 16.dp),
            )
            val item = achievementUiModel.item as AchievementUiModel.AchievementItemUiModel.Money
            Text(
                text = item.displayableAmount(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.achievement_share_money_based_celebratory_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.achievement_share_money_based_celebratory_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun IconView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                shape = CircleShape,
            )
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(com.shahzadian.goalsscreen.R.drawable.ic_achievement_share_money_based_celebratory),
            contentDescription = "celebratory",
        )
    }
}

@Composable
private fun TopStartEmoji(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "⭐",
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun TopEndEmoji(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "\uD83C\uDF8A",
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun BottomStartEmoji(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "✨",
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun BottomEndEmoji(
    modifier: Modifier = Modifier,
) {
    Text(
        text = "\uD83C\uDF89",
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}