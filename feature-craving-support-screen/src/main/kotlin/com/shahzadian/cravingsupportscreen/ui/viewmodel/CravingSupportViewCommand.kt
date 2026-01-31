package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface CravingSupportViewCommand : ViewCommand {

    @Immutable
    data object NavigateBack : CravingSupportViewCommand
}