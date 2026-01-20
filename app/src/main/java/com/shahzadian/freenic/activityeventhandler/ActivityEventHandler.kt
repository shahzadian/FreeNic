package com.shahzadian.freenic.activityeventhandler

import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.ActivityEventResult
import com.freenic.ui.extensions.lazyUnsafe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal abstract class ActivityEventHandler<E : ActivityEvent>(
    private val coroutineScope: CoroutineScope,
) {

    protected abstract val eventClass: KClass<E>

    private val _resultFlow: MutableSharedFlow<ActivityEventResult> by lazyUnsafe {
        MutableSharedFlow()
    }
    val resultFlow: Flow<ActivityEventResult> = _resultFlow

    private val _activityCommandFlow: MutableSharedFlow<ActivityCommand> by lazyUnsafe {
        MutableSharedFlow()
    }
    val activityCommandFlow: Flow<ActivityCommand> = _activityCommandFlow

    fun handle(event: ActivityEvent) {
        if (eventClass.isInstance(event).not()) return
        onEvent(event as E)
    }

    protected abstract fun onEvent(event: E)

    protected fun sendResult(result: ActivityEventResult) {
        coroutineScope.launch {
            _resultFlow.emit(result)
        }
    }

    protected fun sendCommand(command: ActivityCommand) {
        coroutineScope.launch {
            _activityCommandFlow.emit(command)
        }
    }

    protected fun launchInScope(scope: suspend () -> Unit) {
        coroutineScope.launch { scope() }
    }
}