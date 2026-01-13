package com.shahzadian.datafill.domain.fillableInfo.result

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableDataSharedStateHolder
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import kotlinx.coroutines.CoroutineScope

internal class ResultInfoProvider(
    coroutineScope: CoroutineScope,
    private val sharedStateHolder: FillableDataSharedStateHolder,
) : FillableInfoProvider<ResultFillableInfo, ResultFillableInfoEvent>(
    coroutineScope
) {

    override val initState: ResultFillableInfo by lazyUnsafe {
        createFillableInfo()
    }

    override fun validateCurrentInfo(): ResultFillableInfo {
        return createFillableInfo()
    }

    override fun updateSharedState(
        currentInfo: ResultFillableInfo,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState {
        return currentSharedState.copy(
            quitDate = currentInfo.userInfo.quitDateMillis,
        )
    }

    override suspend fun handleFillableInfoEvent(event: ResultFillableInfoEvent): ResultFillableInfo? {
        return currentInfo
    }

    private fun createFillableInfo(): ResultFillableInfo {
        val name = requireNotNull(sharedStateHolder.sharedState.name)
        val age = requireNotNull(sharedStateHolder.sharedState.age)
        val cigarettesPerDay = requireNotNull(sharedStateHolder.sharedState.cigarettesPerDay)
        val quitDateMillis = requireNotNull(sharedStateHolder.sharedState.quitDate)
        return ResultFillableInfo(
            userInfo = ResultFillableInfo.UserInfo(
                name = name,
                age = age,
                cigarettesPerDay = cigarettesPerDay,
                quitDateMillis = quitDateMillis,
            )
        )
    }
}
