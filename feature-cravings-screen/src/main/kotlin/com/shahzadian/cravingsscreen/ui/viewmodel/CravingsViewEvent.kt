package com.shahzadian.cravingsscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewEvent

@Immutable
internal sealed interface CravingsViewEvent : ViewEvent {
}