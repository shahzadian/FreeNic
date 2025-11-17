package com.shahzadian.datafill.domain

import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactory
import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FillableInfoHelperImpl @Inject constructor(
    private val fillableInfoProviderFactory: FillableInfoProviderFactory,
) : FillableInfoHelper {

    private var fillableInfoProviders: List<FillableInfoProvider<*, *>>? = null
    private var coroutineScope: CoroutineScope? = null
    private var currentProviderCollectionJob: Job? = null
    private var _currentInfoFlow: MutableStateFlow<FillableInfo>? = null

    override val infoPagesCount: Int
        get() = fillableInfoProviders?.size ?: 0

    override var currentPageIndex: Int = -1

    override fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        val factory = fillableInfoProviderFactory.getProviders(coroutineScope)
        fillableInfoProviders = factory.toList()
    }

    override suspend fun getNextData(): DataToFill? {
        if (currentPageIndex == fillableInfoProviders?.lastIndex) return null
        return fillableInfoProviders
            ?.let { providers ->
                currentPageIndex += 1
                val info = providers[currentPageIndex].fillableInfoStateFlow.value

                // Setup relay flow to mirror current provider's info updates
                if (_currentInfoFlow == null) {
                    _currentInfoFlow = MutableStateFlow(info)
                } else {
                    _currentInfoFlow?.value = info
                }

                // Switch collection to the new provider
                currentProviderCollectionJob?.cancel()
                coroutineScope?.let { scope ->
                    currentProviderCollectionJob = scope.launch {
                        @Suppress("UNCHECKED_CAST")
                        (providers[currentPageIndex].fillableInfoStateFlow as StateFlow<FillableInfo>)
                            .collect { newInfo ->
                                _currentInfoFlow?.value = newInfo
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

    override val currentInfoFlow: StateFlow<FillableInfo>?
        get() = _currentInfoFlow

    override fun sendEvent(event: FillableInfoEvent) {
        val provider = fillableInfoProviders?.getOrNull(currentPageIndex) ?: return
        @Suppress("UNCHECKED_CAST")
        (provider as FillableInfoProvider<FillableInfo, FillableInfoEvent>)
            .onFillableInfoEvent(event)
    }
}
