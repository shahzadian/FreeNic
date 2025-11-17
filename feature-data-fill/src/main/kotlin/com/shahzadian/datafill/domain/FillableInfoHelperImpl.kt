package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FillableInfoHelperImpl @Inject constructor(
    private val fillableInfoProviderFactory: FillableInfoProviderFactory,
) : FillableInfoHelper {

    private var fillableInfoProviders: List<FillableInfoProvider<*, *>>? = null
    private var coroutineScope: CoroutineScope? = null
    private var currentProviderCollectionJob: Job? = null

    override val infoPagesCount: Int
        get() = fillableInfoProviders?.size ?: 0

    override var currentPageIndex: Int = -1

    override val currentInfoFlow: MutableSharedFlow<FillableInfo> = MutableSharedFlow()

    override fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        val factory = fillableInfoProviderFactory.getProviders(coroutineScope)
        fillableInfoProviders = factory
    }

    override suspend fun getNextData(): DataToFill? {
        if (currentPageIndex == fillableInfoProviders?.lastIndex) return null
        return fillableInfoProviders
            ?.let { providers ->
                currentPageIndex += 1
                val info = providers[currentPageIndex].fillableInfoStateFlow.value

                currentInfoFlow.emit(info)

                // Switch collection to the new provider
                currentProviderCollectionJob?.cancel()
                coroutineScope?.let { scope ->
                    currentProviderCollectionJob = scope.launch {
                        @Suppress("UNCHECKED_CAST")
                        (providers[currentPageIndex].fillableInfoStateFlow as StateFlow<FillableInfo>)
                            .collect { newInfo ->
                                currentInfoFlow.emit(newInfo)
                            }
                    }
                }

                DataToFill(
                    pagesCount = providers.size,
                    currentPageIndex = currentPageIndex,
                    info = info
                )
            }
    }

    override fun sendEvent(event: FillableInfoEvent) {
        val provider = fillableInfoProviders?.getOrNull(currentPageIndex) ?: return
        @Suppress("UNCHECKED_CAST")
        (provider as FillableInfoProvider<FillableInfo, FillableInfoEvent>)
            .onFillableInfoEvent(event)
    }
}
