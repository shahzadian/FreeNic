package com.shahzadian.dashboard.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.shahzadian.dashboard.ui.cigsavoided.model.CigsAvoidedUiModel
import com.shahzadian.dashboard.ui.header.model.HeaderUiModel
import com.shahzadian.dashboard.ui.healthprogress.model.HealthProgressUiModel
import com.shahzadian.dashboard.ui.lungsrecovery.model.LungsRecoveryUiModel
import com.shahzadian.dashboard.ui.nocigdays.model.NoCigDaysUiModel
import com.shahzadian.dashboard.ui.savedmoney.model.SavedMoneyUiModel

@Immutable
internal data class DashboardUiModel(
    val headerUiModel: HeaderUiModel,
    val noCigDaysUiModel: NoCigDaysUiModel,
    val savedMoneyUiModel: SavedMoneyUiModel,
    val healthProgressUiModel: HealthProgressUiModel,
    val lungsRecoveryUiModel: LungsRecoveryUiModel,
    val cigsAvoidedUiModel: CigsAvoidedUiModel,
)
