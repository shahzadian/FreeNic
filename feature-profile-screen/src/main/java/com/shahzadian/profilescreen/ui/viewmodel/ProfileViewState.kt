package com.shahzadian.profilescreen.ui.viewmodel

import com.freenic.ui.vm.ViewState

internal sealed interface ProfileViewState : ViewState {

    data object Loading : ProfileViewState

    data class Data(
        val userNameLetter: String
    ) : ProfileViewState
}
