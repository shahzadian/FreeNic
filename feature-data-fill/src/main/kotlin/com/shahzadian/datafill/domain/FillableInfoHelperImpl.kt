package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactory
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class FillableInfoHelperImpl @Inject constructor(
    private val fillableInfoProviderFactory: FillableInfoProviderFactory,
) : FillableInfoHelper {

    private var fillableInfoProviders: List<FillableInfoProvider<*, *>>? = null

    override val infoPagesCount: Int
        get() = fillableInfoProviders?.size ?: 0

    override var currentPageIndex: Int = -1

    override fun initialize(coroutineScope: CoroutineScope) {
        val factory = fillableInfoProviderFactory.getProviders(coroutineScope)
        fillableInfoProviders = factory.toList()
    }

    override suspend fun getNextData(): DataToFill? {
        if (currentPageIndex == fillableInfoProviders?.lastIndex) return null
        return fillableInfoProviders
            ?.let { providers ->
                currentPageIndex += 1
                DataToFill(
                    pagesCount = providers.size,
                    currentPageIndex = currentPageIndex,
                    info = providers[currentPageIndex].fillableInfoStateFlow.value
                )
            }
    }
}
