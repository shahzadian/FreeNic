package com.shahzadian.datafill.domain.fillableInfo

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Base fillable data interface.
 * All fillable data classes should implement this interface.
 * Use fills data on separata pages (personal data, cigarettes count data etc)
 */
internal abstract class FillableInfoProvider<I : FillableInfo, E : FillableInfoEvent>(
    private val coroutineScope: CoroutineScope,
) {

    protected abstract val initState: I

    private val _infoStateFlow: MutableStateFlow<I> by lazyUnsafe {
        MutableStateFlow(initState)
    }

    val fillableInfoStateFlow: StateFlow<I>
        get() = _infoStateFlow

    protected val currentInfo: I
        get() = fillableInfoStateFlow.value

    fun onFillableInfoEvent(event: E) {
        coroutineScope.launch {
            handleFillableInfoEvent(event)
                ?.let { newState ->
                    _infoStateFlow.value = newState
                }
        }
    }

    fun validateState() {
        validateCurrentInfo()?.let { _infoStateFlow.value = it }
    }

    fun onFillInfoSubmit(
        sharedState: FillableInfoSharedState,
        onSharedStateUpdated: (FillableInfoSharedState) -> Unit
    ) {
        val updatedSharedState = updateSharedState(
            currentInfo = fillableInfoStateFlow.value,
            currentSharedState = sharedState,
        )
        onSharedStateUpdated(updatedSharedState)
    }

    protected abstract suspend fun handleFillableInfoEvent(event: E): I?

    protected open fun validateCurrentInfo(): I? = null

    protected abstract fun updateSharedState(
        currentInfo: I,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState
}

/**
 * Describes info to be filled
 */
internal interface FillableInfo {
    fun isValid(): Boolean
}

/**
 * Describes events that happened during the user interaction with fillable info
 */
internal interface FillableInfoEvent
