package com.shahzadian.goalsscreen.ui.achievementsharescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahzadian.baseui.R

@Composable
internal fun ShareAchievementAppLogo(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier,
            style = MaterialTheme.typography.bodySmall
        )
    }

}
