package com.shahzadian.dashboard.ui.savedmoney.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class SavedMoneyUiModel(
    val savedMoney: Float,
    val currency: String?,
)
