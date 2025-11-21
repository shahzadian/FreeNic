package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedUiModel
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedView
import com.shahzadian.dashboard.ui.header.HeaderView
import com.shahzadian.dashboard.ui.header.model.HeaderUiModel
import com.shahzadian.dashboard.ui.healthprogress.HealthProgressView
import com.shahzadian.dashboard.ui.healthprogress.model.HealthProgressUiModel
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
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .verticalScroll(scrollState),
    ) {
        HeaderView(
            model = HeaderUiModel(
                userName = "David",
            ),
            modifier = Modifier
        )
        NoCigDaysView(
            model = NoCigDaysUiModel(days = 59),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )
        HealthProgressView(
            model = HealthProgressUiModel(
                items = listOf(
                    HealthProgressUiModel.HealthProgressItem(
                        label = TextWrapper.FromRes(
                            R.string.dashboard_health_progress_heart_label
                        ),
                        icon = ImageWrapper.FromRes(
                            com.shahzadian.dashboard.R.drawable.ic_dashboard_health_progress_heart
                        ),
                        progress = 60
                    ),
                    HealthProgressUiModel.HealthProgressItem(
                        label = TextWrapper.FromRes(
                            R.string.dashboard_health_progress_energy_label
                        ),
                        icon = ImageWrapper.FromRes(
                            com.shahzadian.dashboard.R.drawable.ic_dashboard_health_progress_energy
                        ),
                        progress = 40
                    ),
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            CigsAvoidedView(
                model = CigsAvoidedUiModel(
                    count = 100,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
            )
            SavedMoneyView(
                savedMoneyUiModel = SavedMoneyUiModel(
                    savedMoney = 1000,
                    currency = "$"
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
            )
        }
        LungsRecoveryView(
            model = LungsRecoveryUiModel(recoveryPercentage = 80),
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}

