package com.shahzadian.debugpanelscreen.domain

import com.shahzadian.debugparams.domain.DebugParams
import com.shahzadian.debugparams.domain.DebugParamsHelper
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import javax.inject.Inject

internal interface DebugPanelParamsHelper {

    suspend fun getParams(): DebugPanelParams

    suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams
}

internal class DebugPanelParamsHelperImpl @Inject constructor(
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val debugParamsHelper: DebugParamsHelper,
) : DebugPanelParamsHelper {

    override suspend fun getParams(): DebugPanelParams {
        return debugParamsHelper.getParams()?.toPanelParams() ?: DebugPanelParams(
            hasPaidSubscription = getUserCurrentSubscriptionUseCase() != null
        )
    }

    override suspend fun onHasPaidSubscriptionSwitched(isSwitchOn: Boolean): DebugPanelParams {
        return debugParamsHelper
            .onHasPaidSubscriptionSwitched(isSwitchOn = isSwitchOn)
            .toPanelParams()
    }
}

private fun DebugParams.toPanelParams(): DebugPanelParams {
    return DebugPanelParams(
        hasPaidSubscription = hasPaidSubscription
    )
}
