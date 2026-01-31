package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface CravingSupportViewEvent : ViewEvent {

    @Immutable
    data object OnBackIconClicked : CravingSupportViewEvent
}
