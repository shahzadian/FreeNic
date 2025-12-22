package com.shahzadian.debugpanelscreen.domain

import com.shahzadian.debugpanelscreen.data.DebugPanelParamsRepository
import javax.inject.Inject

internal interface DebugPanelParamsHelper {

    suspend fun getParams(): DebugPanelParams?

    suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams
}

internal class DebugPanelParamsHelperImpl @Inject constructor(
    private val debugPanelParamsRepository: DebugPanelParamsRepository
) : DebugPanelParamsHelper {

    override suspend fun getParams(): DebugPanelParams? {
        return debugPanelParamsRepository.getParams()
    }

    override suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams {
        return debugPanelParamsRepository.onHasPaidSubscriptionSwitched(isSwitchOn)
    }
}
