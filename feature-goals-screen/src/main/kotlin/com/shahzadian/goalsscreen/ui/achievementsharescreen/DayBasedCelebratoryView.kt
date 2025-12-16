package com.shahzadian.goalsscreen.ui.achievementsharescreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.Success
import com.shahzadian.goalsscreen.R
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun DayBasedCelebratoryView(
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
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconCircleView(
                modifier = Modifier
                    .padding(bottom = 8.dp),
            )
            TextView(
                achievementUiModel = achievementUiModel,
                modifier = Modifier
                    .padding(bottom = 16.dp),
            )
            ShareAchievementAppLogo(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimary
            )
        }
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
    }
}

@Composable
private fun IconCircleView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                clip = true,
            )
            .size(80.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onSecondary,
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                ),
                shape = CircleShape,
            )
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_share_achievement_celebratory),
            contentDescription = "celebratory",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun TextView(
    achievementUiModel: AchievementUiModel,
    modifier: Modifier = Modifier,
) {
    val days = (achievementUiModel.item as? AchievementUiModel.AchievementItemUiModel.Days)
        ?.count ?: throw IllegalArgumentException("Achievement item is not of type Days")
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = days.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.achievement_share_celebratory_title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.achievement_share_celebratory_subtitle),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
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