package com.shahzadian.datafill.domain.fillableInfo.cigarettesperday

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import kotlinx.coroutines.CoroutineScope

internal class CigarettesPerDayInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<CigarettesPerDayFillableInfo, CigarettesPerDayFillableInfoEvent>(
    coroutineScope
) {

    private val cigarettesCountIntRange: IntRange = IntRange(1, 40)
    private var cigarettesPerDay: Int = cigarettesCountIntRange.last / 2

    override val initState: CigarettesPerDayFillableInfo by lazyUnsafe {
        CigarettesPerDayFillableInfo(
            cigarettesCountIntRange = cigarettesCountIntRange,
            cigarettesPerDay = cigarettesPerDay,
        )
    }

    override fun onFillInfoSubmit() {

    }

    override suspend fun handleFillableInfoEvent(event: CigarettesPerDayFillableInfoEvent): CigarettesPerDayFillableInfo? {
        return when (event) {
            is CigarettesPerDayFillableInfoEvent.OnCigarettesPerDayChanged -> {
                currentInfo.copy(
                    cigarettesPerDay = event.cigarettesPerDay.toInt()
                )
            }

            is CigarettesPerDayFillableInfoEvent.OnPriceChanged -> {
                currentInfo
            }
        }
    }
}