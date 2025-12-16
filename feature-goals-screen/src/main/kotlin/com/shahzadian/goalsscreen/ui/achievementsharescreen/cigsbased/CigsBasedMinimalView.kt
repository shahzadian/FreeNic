package com.shahzadian.goalsscreen.ui.achievementsharescreen.cigsbased

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.goalsscreen.R
import com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementAppLogo
import com.shahzadian.goalsscreen.ui.achievementsharescreen.ShareAchievementCard
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun CigsBasedMinimalView(
    achievementUiModel: AchievementUiModel,
    isSelected: Boolean,
    shouldRecord: Boolean,
    onCardClicked: () -> Unit,
    onRecorded: (Bitmap) -> Unit,
    modifier: Modifier = Modifier,
) {
    ShareAchievementCard(
        isSelected = isSelected,
        shouldRecord = shouldRecord,
        containerBrush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.background,
            )
        ),
        modifier = modifier,
        onCardClick = onCardClicked,
        onRecorded = onRecorded,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircleView(
                modifier = Modifier
                    .padding(bottom = 16.dp),
            )
            TextView(
                cigsCount = (achievementUiModel.item as? AchievementUiModel.AchievementItemUiModel.Cigs)?.count
                    ?: throw IllegalStateException("Achievement item ui model must be of cigs type")
            )
        }
    }
}

@Composable
private fun CircleView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                    ),
                ),
                shape = CircleShape,
            )
            .size(80.dp)
            .padding(8.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_achievement_share_minimal_cigs),
            contentDescription = "share_minimal_cigs",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun TextView(
    cigsCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = cigsCount.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayLarge,
        )
        Text(
            text = pluralStringResource(
                com.shahzadian.baseui.R.plurals.achievement_share_minimal_cigs_title,
                cigsCount,
                cigsCount
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = pluralStringResource(
                com.shahzadian.baseui.R.plurals.achievement_share_minimal_cigs_subtitle,
                cigsCount,
                cigsCount
            ),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        ShareAchievementAppLogo(
            backgroundColor = null
        )
    }
}