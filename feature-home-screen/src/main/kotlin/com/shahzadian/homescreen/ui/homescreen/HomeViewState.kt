package com.shahzadian.homescreen.ui.homescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
data class HomeViewState(
    val isLoading: Boolean,
) : ViewState
