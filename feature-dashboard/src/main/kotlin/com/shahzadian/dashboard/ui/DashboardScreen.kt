package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzadian.dashboard.ui.lungsrecovery.LungsRecoveryView
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryUiModel
import com.shahzadian.dashboard.ui.nocigdays.NoCigDaysView
import com.shahzadian.dashboard.ui.nocigdays.model.NoCigDaysUiModel
import com.shahzadian.dashboard.ui.savedmoney.SavedMoneyView
import com.shahzadian.dashboard.ui.savedmoney.model.SavedMoneyUiModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        NoCigDaysView(
            model = NoCigDaysUiModel(days = 59),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        )
        SavedMoneyView(
            savedMoneyUiModel = SavedMoneyUiModel(
                savedMoney = 1000,
                currency = "$"
            ),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        LungsRecoveryView(
            model = LungsRecoveryUiModel(recoveryPercentage = 80),
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}

