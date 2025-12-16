package com.shahzadian.goalsscreen.ui.achievementsharescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

@Composable
internal fun ShareAchievementAppLogo(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    style: TextStyle = MaterialTheme.typography.bodySmall
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor ?: Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = textColor,
            modifier = modifier,
            style = style,
        )
    }
}

@Composable
fun ShareAchievementCard(
    isSelected: Boolean,
    containerBrush: Brush,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val cardClick = remember(Unit) { { onCardClick() } }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isSelected) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        } else {
            null
        },
        onClick = cardClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = containerBrush,
                    shape = MaterialTheme.shapes.small
                )
                .padding(16.dp)
        ) {
            content()
            if (isSelected) {
                Image(
                    painter = painterResource(com.shahzadian.goalsscreen.R.drawable.ic_share_achievement_check),
                    contentDescription = "check",
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}
