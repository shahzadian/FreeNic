package com.shahzadian.datafill.domain.fillableInfo.result

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import kotlinx.coroutines.CoroutineScope

internal class ResultInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<ResultFillableInfo, ResultFillableInfoEvent>(
    coroutineScope
) {

    override val initState: ResultFillableInfo by lazyUnsafe {
        ResultFillableInfo(
            userInfo = ResultFillableInfo.UserInfo(
                name = "some name",
                age = 25,
                cigarettesPerDay = 20
            )
        )
    }

    override fun onFillInfoSubmit() {
        // No-op for now
    }

    override suspend fun handleFillableInfoEvent(event: ResultFillableInfoEvent): ResultFillableInfo? {
        return currentInfo
    }
}
