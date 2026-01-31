package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CravingSupportViewModel @Inject constructor() :
    BaseViewModel<CravingSupportViewState, CravingSupportViewEvent, CravingSupportViewCommand>() {

    override val initState: CravingSupportViewState by lazyUnsafe {
        CravingSupportViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: CravingSupportViewEvent) {
        when (event) {
            is CravingSupportViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            setViewState(CravingSupportViewState.Data)
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(CravingSupportViewCommand.NavigateBack)
        }
    }
}
