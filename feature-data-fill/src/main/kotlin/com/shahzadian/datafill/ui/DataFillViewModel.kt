package com.shahzadian.datafill.ui

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DataFillViewModel @Inject constructor() :
    BaseViewModel<DataFillViewState, DataFillViewEvent, DataFillCommand>() {

    // Initialize with About state to keep and expose name and age
    override val initState: DataFillViewState by lazyUnsafe {
        DataFillViewState.About()
    }

    override fun onViewEventHappened(event: DataFillViewEvent) {
        when (event) {
            is DataFillViewEvent.OnNameChanged -> updateName(event.name)
            is DataFillViewEvent.OnAgeChanged -> updateAge(event.age)
        }
    }

    private fun updateName(name: String) {
        val current = viewStateFlow.value
        if (current is DataFillViewState.About) {
            viewModelScope.launch {
                setViewState(current.copy(name = name))
            }
        }
    }

    private fun updateAge(age: String) {
        val current = viewStateFlow.value
        if (current is DataFillViewState.About) {
            viewModelScope.launch {
                setViewState(current.copy(age = age))
            }
        }
    }
}