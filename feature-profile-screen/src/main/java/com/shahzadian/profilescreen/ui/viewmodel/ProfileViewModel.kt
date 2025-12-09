package com.shahzadian.profilescreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
) : BaseViewModel<ProfileViewState, ProfileViewEvent, ProfileViewCommand>() {

    override val initState: ProfileViewState by lazyUnsafe { ProfileViewState.Loading }

    init {
        updateProfileData()
    }

    override fun onViewEventHappened(event: ProfileViewEvent) {

    }

    private fun updateProfileData() {
        viewModelScope.launch {
            val userData =
                try {
                    getUserDataUseCase() ?: throw IllegalStateException("Couldn't get user data")
                } catch (t: Throwable) {
//                    TODO add error state
                    return@launch
                }
            val userNameLetter = userData.personalData.name.first().toString()
            setViewState(
                ProfileViewState.Data(
                    userNameLetter = userNameLetter
                )
            )
        }
    }
}
