package com.shahzadian.debugparams.domain

import com.shahzadian.debugparams.data.DebugParamsRepository
import javax.inject.Inject

interface DebugParamsHelper {

    suspend fun getParams(): DebugParams?

    suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugParams
}

internal class DebugParamsHelperImpl @Inject constructor(
    private val debugParamsRepository: DebugParamsRepository,
) : DebugParamsHelper {

    override suspend fun getParams(): DebugParams? {
        return debugParamsRepository.getParams()
    }

    override suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugParams {
        return debugParamsRepository.onHasPaidSubscriptionSwitched(isSwitchOn)
    }
}
