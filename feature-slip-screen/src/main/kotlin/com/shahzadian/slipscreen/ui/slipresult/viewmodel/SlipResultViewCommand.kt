package com.shahzadian.slipscreen.ui.slipresult.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface SlipResultViewCommand : ViewCommand {

    @Immutable
    data class NavigateTo(val screen: AppScreen) : SlipResultViewCommand
}
