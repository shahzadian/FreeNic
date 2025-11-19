package com.shahzadian.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryUiModel
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryView

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
) {
    LungsRecoveryView(
        model = LungsRecoveryUiModel(recoveryPercentage = 80),
        modifier = modifier,
    )
}

