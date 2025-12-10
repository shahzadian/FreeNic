package com.shahzadian.subscription.paywall.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.imageFromWrapper
import com.freenic.ui.extensions.textFromWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.subscription.paywall.ui.viewmodel.PaywallViewState

@Composable
internal fun PaidFeatureView(
    uiModel: PaywallViewState.PaidFeatureUiModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(
            width = 0.2.dp,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Row {
                Image(
                    painter = imageFromWrapper(uiModel.icon),
                    contentDescription = "Paid feature",
                    modifier = Modifier.padding(end = 8.dp)
                )
                TextView(
                    title = uiModel.title,
                    subtitle = uiModel.subtitle
                )
            }
        }
    }
}

@Composable
private fun TextView(
    title: TextWrapper,
    subtitle: TextWrapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = textFromWrapper(title),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = textFromWrapper(subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
