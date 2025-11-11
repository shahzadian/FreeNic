package com.shahzadian.homescreen.ui.homescreen

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
sealed interface HomeScreenCommand : ViewCommand {
    data class Navigate(
        val screenId: String,
        val shouldRemoveCurrentScreenFromStack: Boolean,
    ) : HomeScreenCommand
}
