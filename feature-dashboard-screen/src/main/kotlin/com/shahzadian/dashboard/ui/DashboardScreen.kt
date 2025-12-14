package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.freenic.ui.extensions.HeaderScreen
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedView
import com.shahzadian.dashboard.ui.healthprogress.HealthProgressView
import com.shahzadian.dashboard.ui.lungsrecovery.LungsRecoveryView
import com.shahzadian.dashboard.ui.nocigdays.NoCigDaysView
import com.shahzadian.dashboard.ui.savedmoney.SavedMoneyView
import com.shahzadian.dashboard.ui.viewmodel.DashboardUiModel
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewModel
import com.shahzadian.dashboard.ui.viewmodel.DashboardViewState

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsState()
    when (state) {
        is DashboardViewState.Loading -> {}
        is DashboardViewState.Data -> {
            ContentView(
                uiModel = (state as DashboardViewState.Data).uiModel,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ContentView(
    uiModel: DashboardUiModel,
    modifier: Modifier = Modifier,
) {
    HeaderScreen(
        title = TextWrapper.FromRes(
            R.string.dashboard_header_title,
            arrayListOf(uiModel.headerUiModel.userName)
        ),
        subtitle = TextWrapper.FromRes(R.string.dashboard_header_subtitle),
        modifier = modifier
    ) {
        NoCigDaysView(
            model = uiModel.noCigDaysUiModel,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )
        HealthProgressView(
            model = uiModel.healthProgressUiModel,
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
                model = uiModel.cigsAvoidedUiModel,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
            )
            SavedMoneyView(
                savedMoneyUiModel = uiModel.savedMoneyUiModel,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
            )
        }
        LungsRecoveryView(
            model = uiModel.lungsRecoveryUiModel,
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}