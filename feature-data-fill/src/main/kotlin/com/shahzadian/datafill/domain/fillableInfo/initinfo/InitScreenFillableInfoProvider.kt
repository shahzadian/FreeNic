package com.shahzadian.datafill.domain.fillableInfo.initinfo

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import kotlinx.coroutines.CoroutineScope

internal class InitScreenFillableInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<InitScreenFillableInfo, InitScreenFillableInfoEvent>(
    coroutineScope = coroutineScope
) {

    override val initState: InitScreenFillableInfo by lazyUnsafe {
        InitScreenFillableInfo
    }

    override suspend fun handleFillableInfoEvent(event: InitScreenFillableInfoEvent): InitScreenFillableInfo? {
//        Nothing
        return null
    }

    override fun updateSharedState(
        currentInfo: InitScreenFillableInfo,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState {
        return currentSharedState
    }
}
