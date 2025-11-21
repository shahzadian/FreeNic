package com.shahzadian.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedView
import com.shahzadian.dashboard.ui.header.HeaderView
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
            model = uiModel.headerUiModel,
            modifier = Modifier
        )
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