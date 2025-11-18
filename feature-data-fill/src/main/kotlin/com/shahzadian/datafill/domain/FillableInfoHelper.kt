package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

internal interface FillableInfoHelper {

    val infoPagesCount: Int

    val currentPageIndex: Int

    /**
     * Exposes current provider's info as a flow so UI can observe updates while user types.
     */
    val currentInfoFlow: Flow<FillableInfo>

    fun initialize(coroutineScope: CoroutineScope)

    suspend fun getNextData(): DataToFill?

    /**
     * Moves to the previous fillable info page (if possible) and emits its current info.
     * Returns null when already at the first page or providers are not initialized.
     */
    suspend fun getPreviousData(): DataToFill?

    /**
     * Sends a user interaction event to the current provider.
     */
    fun sendEvent(event: FillableInfoEvent)
}
