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
        val userNameLetter: String,
        val appVersion: String,
        val personalData: PersonalData,
        val smokingDetails: SmokingDetails,
    ) : ProfileViewState {

        @Immutable
        data class PersonalData(
            val name: String,
            val age: Int,
            val quitDate: String,
        )

        @Immutable
        data class SmokingDetails(
            val cigarettesPerDay: Int,
            val price: Float,
            val currency: String?,
        )
    }
}
