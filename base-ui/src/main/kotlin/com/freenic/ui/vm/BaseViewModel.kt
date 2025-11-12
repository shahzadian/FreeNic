package com.freenic.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

abstract class BaseViewModel<VS : ViewState, VE : ViewEvent, VC : ViewCommand> : ViewModel() {

    protected abstract val initState: VS

    private val _viewStateFlow: MutableStateFlow<VS> by lazy(LazyThreadSafetyMode.NONE) {
        MutableStateFlow(initState)
    }
    val viewStateFlow: MutableStateFlow<VS>
        get() = _viewStateFlow

    private val _viewCommandFlow: MutableSharedFlow<VC> = MutableSharedFlow()
    val viewCommandFlow: MutableSharedFlow<VC>
        get() = _viewCommandFlow

    fun onViewEvent(event: VE) {
        onViewEventHappened(event)
    }

    protected abstract fun onViewEventHappened(event: VE)

    protected suspend fun setViewState(state: VS) {
        withContext(Dispatchers.Main) {
            _viewStateFlow.emit(state)
        }
    }

    protected suspend fun sendViewCommand(command: VC) {
        withContext(Dispatchers.Main) {
            _viewCommandFlow.emit(command)
        }
    }
}
