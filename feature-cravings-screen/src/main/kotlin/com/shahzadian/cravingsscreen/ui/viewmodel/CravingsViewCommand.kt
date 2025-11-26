package com.shahzadian.cravingsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.AppScreen
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface CravingsViewCommand : ViewCommand {

    data class NavigateTo(val screen: AppScreen) : CravingsViewCommand
}
