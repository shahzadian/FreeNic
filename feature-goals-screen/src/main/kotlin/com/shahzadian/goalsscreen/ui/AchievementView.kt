package com.shahzadian.goalsscreen.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.goalsscreen.R
import com.shahzadian.goalsscreen.ui.viewmodel.AchievementUiModel

@Composable
internal fun AchievementView(
    achievement: AchievementUiModel,
    modifier: Modifier = Modifier,
) {
    val isAchievementUnlocked =
        achievement.state is AchievementUiModel.AchievementStateUiModel.Unlocked
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAchievementUnlocked) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
    ) {
        Box(
            modifier = Modifier,
        ) {
            if (!isAchievementUnlocked) {
                Image(
                    painter = painterResource(R.drawable.ic_achievement_lock),
                    contentDescription = "Premium lock",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .defaultMinSize(
                        minHeight = 150.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = imageFromWrapper(achievement.icon),
                    contentDescription = "Achievement",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(35.dp)
                        .align(Alignment.CenterHorizontally),
                )
                Text(
                    text = textFromWrapper(achievement.title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = textFromWrapper(achievement.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                AchievementProgressView(
                    progressPercents = achievement.state.progressPercents,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun AchievementProgressView(
    progressPercents: Float,
    modifier: Modifier = Modifier,
) {
    // Convert percent (0..100) to fraction (0..1) and clamp
    val normalizedTarget = (progressPercents / 100f).coerceIn(0f, 1f)

    // Ensure entry animation as well: start from 0f, then animate to actual value
    var displayedTarget by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(normalizedTarget) {
        displayedTarget = normalizedTarget
    }

    val animatedProgress by animateFloatAsState(
        targetValue = displayedTarget,
        animationSpec = tween(
            durationMillis = 2500,
            easing = FastOutSlowInEasing,
        ),
        label = "achievement_progress_animation"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .height(10.dp)
            .clip(MaterialTheme.shapes.small),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}
