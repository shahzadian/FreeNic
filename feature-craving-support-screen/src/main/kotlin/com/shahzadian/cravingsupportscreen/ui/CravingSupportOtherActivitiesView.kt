package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.cravingsupportscreen.ui.viewmodel.CravingSupportViewState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CravingSupportOtherActivitiesView(
    activities: ImmutableList<CravingSupportViewState.CravingSupportActivityUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.cravings_support_other_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        activities
            .forEachIndexed { index, activity ->
                ItemView(
                    activity = activity,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = if (index == activities.lastIndex) 0.dp else 8.dp
                        )
                )
            }
    }
}

@Composable
private fun ItemView(
    activity: CravingSupportViewState.CravingSupportActivityUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imageFromWrapper(activity.icon),
            contentDescription = "Activity icon",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = textFromWrapper(activity.name),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}
