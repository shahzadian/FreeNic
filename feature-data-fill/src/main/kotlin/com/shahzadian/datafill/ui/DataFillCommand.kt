package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewCommand

@Immutable
internal sealed interface DataFillCommand : ViewCommand {
}
