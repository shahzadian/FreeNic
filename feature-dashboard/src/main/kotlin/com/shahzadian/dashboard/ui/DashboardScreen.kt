package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedUiModel
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedView
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
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(220.dp)
        ) {
            CigsAvoidedView(
                model = CigsAvoidedUiModel(
                    count = 100,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .fillMaxHeight(),
            )
            SavedMoneyView(
                savedMoneyUiModel = SavedMoneyUiModel(
                    savedMoney = 1000,
                    currency = "$"
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .fillMaxHeight(),
            )
        }
        LungsRecoveryView(
            model = LungsRecoveryUiModel(recoveryPercentage = 80),
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}

