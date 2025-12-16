package com.shahzadian.goalsscreen.ui.achievementsharescreen.daybased

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.theme.Smoke
import com.shahzadian.goalsscreen.R
import com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementCard
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun DayBasedMotivationalView(
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
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconView(
                modifier = Modifier
                    .padding(bottom = 16.dp),
            )
            TextView(
                achievementUiModel = achievementUiModel,
                modifier = Modifier
                    .padding(bottom = 16.dp),
            )
            _root_ide_package_.com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementAppLogo(
                backgroundColor = null,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                color = Color.White.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_share_achievement_motivational),
            contentDescription = "motivational",
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
    val title = pluralStringResource(
        com.shahzadian.baseui.R.plurals.achievement_share_motivational_days_title,
        days,
        days
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.achievement_share_motivational_days_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}