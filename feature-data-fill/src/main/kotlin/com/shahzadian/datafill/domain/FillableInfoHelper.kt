package com.shahzadian.datafill.domain

import kotlinx.coroutines.CoroutineScope

internal interface FillableInfoHelper {

    val infoPagesCount: Int

    val currentPageIndex: Int

    fun initialize(coroutineScope: CoroutineScope)

    suspend fun getNextData(): DataToFill?
}
