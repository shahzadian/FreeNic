package com.shahzadian.profilescreen.managesubscription

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainCardBackground
import com.freenic.ui.extensions.textFromWrapper
import com.shahzadian.baseui.R
import com.shahzadian.profilescreen.managesubscription.viewmodel.ManageSubscriptionViewState

@Composable
internal fun PaidFeaturesView(
    data: ManageSubscriptionViewState.Data,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .mainCardBackground()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(R.string.manage_subscription_paid_features_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Column {
            data.paidFeatures.forEachIndexed { index, feature ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("• ")
                        }
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSecondary)) {
                            append(textFromWrapper(feature))
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = if (index == data.paidFeatures.lastIndex) 0.dp else 8.dp)
                )
            }
        }
    }
}