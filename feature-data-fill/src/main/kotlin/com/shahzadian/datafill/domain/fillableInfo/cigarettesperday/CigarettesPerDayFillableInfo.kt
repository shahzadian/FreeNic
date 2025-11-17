package com.shahzadian.datafill.domain.fillableInfo.cigarettesperday

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import com.shahzadian.datafill.ui.DataFillViewState.CigarettesPerDay.PriceData

internal data class CigarettesPerDayFillableInfo(
    val cigarettesCountIntRange: IntRange,
    val cigarettesPerDay: String? = null,
    val packsPerDay: String? = null,
    val priceData: PriceData? = null,
) : FillableInfo

internal sealed interface CigarettesPerDayFillableInfoEvent : FillableInfoEvent {
    data class OnCigarettesPerDayChanged(val cigarettesPerDay: String) : CigarettesPerDayFillableInfoEvent
    data class OnPacksPerDayChanged(val packsPerDay: String) : CigarettesPerDayFillableInfoEvent
    data class OnPriceChanged(val price: String, val currency: String) : CigarettesPerDayFillableInfoEvent
}
