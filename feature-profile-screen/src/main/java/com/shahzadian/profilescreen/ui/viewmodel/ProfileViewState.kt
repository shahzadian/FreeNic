package com.shahzadian.profilescreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface ProfileViewState : ViewState {

    @Immutable
    data object Loading : ProfileViewState

    @Immutable
    data class Data(
        val userNameLetter: TextWrapper,
        val personalData: PersonalData,
    ) : ProfileViewState {

        @Immutable
        data class PersonalData(
            val name: TextWrapper,
            val age: TextWrapper,
            val quitDate: TextWrapper,
        )
    }
}
