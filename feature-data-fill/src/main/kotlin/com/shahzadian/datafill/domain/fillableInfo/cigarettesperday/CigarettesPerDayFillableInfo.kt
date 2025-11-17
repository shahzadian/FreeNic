package com.shahzadian.datafill.domain.fillableInfo.cigarettesperday

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent

internal data class CigarettesPerDayFillableInfo(
    val cigarettesCountIntRange: IntRange,
    val cigarettesPerDay: Int,
) : FillableInfo {

    val packsPerDay: Float
        get() = cigarettesPerDay.toFloat() / 20
}

internal sealed interface CigarettesPerDayFillableInfoEvent : FillableInfoEvent {
    data class OnCigarettesPerDayChanged(val cigarettesPerDay: String) :
        CigarettesPerDayFillableInfoEvent

    data class OnPriceChanged(val price: String, val currency: String) :
        CigarettesPerDayFillableInfoEvent
}
