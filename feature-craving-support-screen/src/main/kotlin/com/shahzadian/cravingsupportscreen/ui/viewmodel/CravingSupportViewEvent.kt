package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface CravingSupportViewEvent : ViewEvent {

    @Immutable
    data object OnBackIconClicked : CravingSupportViewEvent

    @Immutable
    data class OnCravingStrengthSelected(val strength: CravingSupportViewState.CravingStrength) : CravingSupportViewEvent

    @Immutable
    data class OnActivityClicked(val id: String) : CravingSupportViewEvent
}
