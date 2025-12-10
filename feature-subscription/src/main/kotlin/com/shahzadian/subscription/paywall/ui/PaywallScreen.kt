package com.shahzadian.subscription.paywall.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freenic.ui.extensions.mainGradientBackground
import com.shahzadian.baseui.R

@Composable
fun PaywallScreen(
    modifier: Modifier = Modifier,
) {
    ContentView(
        modifier = modifier
            .fillMaxSize()
            .background(mainGradientBackground())
            .padding(16.dp),
    )
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HeaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(com.shahzadian.subscription.R.drawable.ic_paywall_banner),
            contentDescription = "paywall banner",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun HeaderView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(com.shahzadian.subscription.R.drawable.ic_paywall_header),
                contentDescription = "paywall header",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.paywall_header_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Text(
            text = stringResource(R.string.paywall_header_subtitle),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
