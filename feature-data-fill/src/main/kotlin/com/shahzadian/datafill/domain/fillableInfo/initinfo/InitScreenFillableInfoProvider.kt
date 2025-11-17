package com.shahzadian.datafill.domain.fillableInfo.initinfo

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
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

    override fun onFillInfoSubmit() {
//        Nothing
    }
}
