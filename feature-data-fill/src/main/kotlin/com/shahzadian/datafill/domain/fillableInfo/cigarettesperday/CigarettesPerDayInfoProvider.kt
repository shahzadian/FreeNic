package com.shahzadian.datafill.domain.fillableInfo.cigarettesperday

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import kotlinx.coroutines.CoroutineScope

internal class CigarettesPerDayInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<CigarettesPerDayFillableInfo, CigarettesPerDayFillableInfoEvent>(
    coroutineScope
) {

    override val initState: CigarettesPerDayFillableInfo by lazyUnsafe {
        CigarettesPerDayFillableInfo(
            cigarettesCountIntRange = IntRange(1, 40),
            cigarettesPerDay = null,
            packsPerDay = null,
            priceData = null,
        )
    }

    override fun onFillInfoSubmit() {

    }

    override suspend fun handleFillableInfoEvent(event: CigarettesPerDayFillableInfoEvent): CigarettesPerDayFillableInfo? {
        return when (event) {
            is CigarettesPerDayFillableInfoEvent.OnCigarettesPerDayChanged -> {
                val valueInt = event.cigarettesPerDay.toIntOrNull()
                val clamped = valueInt?.coerceIn(currentInfo.cigarettesCountIntRange.first, currentInfo.cigarettesCountIntRange.last)
                currentInfo.copy(cigarettesPerDay = clamped?.toString())
            }

            is CigarettesPerDayFillableInfoEvent.OnPacksPerDayChanged -> {
                currentInfo.copy(packsPerDay = event.packsPerDay)
            }

            is CigarettesPerDayFillableInfoEvent.OnPriceChanged -> {
                currentInfo
            }
        }
    }
}