package com.shahzadian.profilescreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.QuitData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale
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
                    userNameLetter = TextWrapper.FromString(userNameLetter),
                    personalData = ProfileViewState.Data.PersonalData(
                        name = userData.personalData.name,
                        age = userData.personalData.age,
                        quitDate = userData.quitData.extractDisplayableQuitDate()
                    ),
                    smokingDetails = ProfileViewState.Data.SmokingDetails(
                        cigarettesPerDay = userData.cigarettesData.cigarettesPerDay,
                        price = userData.cigarettesData.priceData.price,
                        currency = if (userData.cigarettesData.priceData.isValidCurrency()) Currency.getInstance(
                            userData.cigarettesData.priceData.currency
                        ).symbol else null
                    )
                )
            )
        }
    }

    private fun QuitData.extractDisplayableQuitDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return Instant.ofEpochMilli(quitDateMillis)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }
}
