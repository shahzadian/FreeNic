package com.shahzadian.goalsscreen.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.goalsscreen.ui.viewmodel.GoalsViewState

@Composable
internal fun AchievementView(
    achievement: GoalsViewState.AchievementUiModel,
    minHeight: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .defaultMinSize(
                    minHeight = minHeight.dp
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
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "achievement_progress_animation"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        color = MaterialTheme.colorScheme.secondary,
        // Ensure the remaining part is clearly visible when not completed
        trackColor = MaterialTheme.colorScheme.outline
    )
}
