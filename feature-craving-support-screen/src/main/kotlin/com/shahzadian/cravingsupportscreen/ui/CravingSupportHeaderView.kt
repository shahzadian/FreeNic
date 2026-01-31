package com.shahzadian.cravingsupportscreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shahzadian.cravingsupportscreen.R

@Composable
internal fun CravingSupportHeaderView(
    animationDuration: Int,
    modifier: Modifier = Modifier,
) {
    var isHeaderVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isHeaderVisible = true
    }
    AnimatedVisibility(
        visible = isHeaderVisible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = animationDuration)
        ),
        modifier = modifier,
    ) {
        HeaderContentView(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderContentView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_craving_support_header),
            contentDescription = "craving support",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_header_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(com.shahzadian.baseui.R.string.craving_support_header_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
        )
    }
}