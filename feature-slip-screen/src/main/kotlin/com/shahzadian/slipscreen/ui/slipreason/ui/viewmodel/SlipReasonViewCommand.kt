package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface SlipReasonViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : SlipReasonViewCommand

    @Immutable
    data class NavigateTo(val screen: com.freenic.ui.AppScreen) : SlipReasonViewCommand
}
