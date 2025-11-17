package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

internal interface FillableInfoHelper {

    val infoPagesCount: Int

    val currentPageIndex: Int

    fun initialize(coroutineScope: CoroutineScope)

    suspend fun getNextData(): DataToFill?

    /**
     * Exposes current provider's info as a flow so UI can observe updates while user types.
     */
    val currentInfoFlow: StateFlow<FillableInfo>?

    /**
     * Sends a user interaction event to the current provider.
     */
    fun sendEvent(event: FillableInfoEvent)
}
