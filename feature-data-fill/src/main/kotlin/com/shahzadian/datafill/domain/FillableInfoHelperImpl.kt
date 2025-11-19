package com.shahzadian.datafill.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProviderFactory
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableDataSharedStateHolder
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
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

    override val sharedState: FillableInfoSharedState
        get() = sharedStateHolder.sharedState

    override val currentInfoFlow: MutableSharedFlow<FillableInfo> = MutableSharedFlow()

    private val sharedStateHolder: FillableDataSharedStateHolder by lazyUnsafe {
        FillableDataSharedStateHolder()
    }

    override fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        val factory = fillableInfoProviderFactory.getProviders(
            coroutineScope = coroutineScope,
            sharedStateHolder = sharedStateHolder,
        )
        fillableInfoProviders = factory
    }

    override suspend fun getNextData(): DataToFill? {
        if (currentPageIndex == fillableInfoProviders?.lastIndex) return null
        return fillableInfoProviders
            ?.let { providers ->
                if (currentPageIndex in 0..providers.lastIndex) {
                    providers[currentPageIndex].onFillInfoSubmit(
                        sharedState = sharedStateHolder.sharedState,
                    ) { updatedState ->
                        sharedStateHolder.sharedState = updatedState
                    }
                }
                currentPageIndex += 1
                val provider = providers[currentPageIndex]
                provider.validateState()
                val info = provider.fillableInfoStateFlow.value

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

    override suspend fun getPreviousData(): DataToFill? {
        val providers = fillableInfoProviders ?: return null
        if (currentPageIndex <= 0) return null

        currentPageIndex -= 1
        val info = providers[currentPageIndex].fillableInfoStateFlow.value

        // Emit current info of the previous provider
        currentInfoFlow.emit(info)

        // Switch collection to the previous provider
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

        return DataToFill(
            pagesCount = providers.size,
            currentPageIndex = currentPageIndex,
            info = info
        )
    }

    override fun sendEvent(event: FillableInfoEvent) {
        val provider = fillableInfoProviders?.getOrNull(currentPageIndex) ?: return
        @Suppress("UNCHECKED_CAST")
        (provider as FillableInfoProvider<FillableInfo, FillableInfoEvent>)
            .onFillableInfoEvent(event)
    }
}
