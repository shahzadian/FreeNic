package com.shahzadian.savingsgoalscreen.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainHintBackground
import com.shahzadian.savingsgoalscreen.R

@Composable
internal fun SavingsGoalDetailsInfoView(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .mainHintBackground()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_savings_goal_details_info),
            contentDescription = "Info",
            modifier = Modifier.padding(end = 8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.savings_goal_details_info),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
