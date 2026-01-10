package com.shahzadian.profilescreen.ui.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.extractMainDisplayableDate
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.baseui.R
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val appInfoHelper: AppInfoHelper,
) : BaseViewModel<ProfileViewState, ProfileViewEvent, ProfileViewCommand>() {

    override val initState: ProfileViewState by lazyUnsafe { ProfileViewState.Loading }

    init {
        updateProfileData()
    }

    override fun onViewEventHappened(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.OnPremiumClicked -> {
                viewModelScope.launch {
                    sendViewCommand(ProfileViewCommand.NavigateTo(AppScreen.PaywallScreen))
                }
            }

            is ProfileViewEvent.OnQuitDateSelected -> {
                onQuitDateUpdated(event)
            }

            is ProfileViewEvent.OnNameEdited -> onNameEdited(event.name)
            is ProfileViewEvent.OnAgeEdited -> onAgeEdited(event.age)
            is ProfileViewEvent.OnCigsPerDayEdited -> onCigsPerDayEdited(event.cigarettesPerDay)
            is ProfileViewEvent.OnPriceEdited -> onPriceEdited(event.price)
            is ProfileViewEvent.OnDebugPanelOpenClicked -> onDebugPanelClicked()
        }
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
            val appName = context.getString(R.string.app_name)
            val appVersion = appInfoHelper.getAppVersion()
            val displayableVersion = "$appName v${appVersion.versionName}"
            setViewState(
                ProfileViewState.Data(
                    userNameLetter = userNameLetter,
                    appVersion = displayableVersion,
//                    TODO
                    isPremiumActive = false,
                    personalData = ProfileViewState.Data.PersonalData(
                        name = userData.personalData.name,
                        age = userData.personalData.age,
                        quitDate = extractMainDisplayableDate(userData.quitData.quitDateMillis),
                        quitDateMillis = userData.quitData.quitDateMillis,
                    ),
                    smokingDetails = ProfileViewState.Data.SmokingDetails(
                        cigarettesPerDay = userData.cigarettesData.cigarettesPerDay,
                        price = userData.cigarettesData.priceData.price,
                        currency = userData.cigarettesData.priceData.getValidCurrencyOrNull()?.symbol
                    ),
                    isDebugBuild = appInfoHelper.isDebugBuild(),
                )
            )
        }
    }

    private fun onQuitDateUpdated(event: ProfileViewEvent.OnQuitDateSelected) {
        viewModelScope.launch {
            val currentState = viewStateFlow.value
            if (currentState is ProfileViewState.Data) {
                val formatted = extractMainDisplayableDate(event.millis)
                val updated = currentState.copy(
                    personalData = currentState.personalData.copy(
                        quitDate = formatted,
                        quitDateMillis = event.millis
                    )
                )
                setViewState(updated)
            }
            updateUserDataUseCase.updateQuidDate(event.millis)
        }
    }

    private fun onNameEdited(name: String) {
        viewModelScope.launch {
            // Update UI state
            val current = viewStateFlow.value
            if (current is ProfileViewState.Data) {
                setViewState(current.copy(personalData = current.personalData.copy(name = name)))
            }
            // Persist
            val userData = getUserDataUseCase() ?: return@launch
            val updated = UserData(
                personalData = UserPersonalData(name = name, age = userData.personalData.age),
                cigarettesData = userData.cigarettesData,
                quitData = userData.quitData,
            )
            updateUserDataUseCase(updated)
        }
    }

    private fun onAgeEdited(age: Int) {
        viewModelScope.launch {
            val current = viewStateFlow.value
            if (current is ProfileViewState.Data) {
                setViewState(current.copy(personalData = current.personalData.copy(age = age)))
            }
            val userData = getUserDataUseCase() ?: return@launch
            val updated = UserData(
                personalData = UserPersonalData(name = userData.personalData.name, age = age),
                cigarettesData = userData.cigarettesData,
                quitData = userData.quitData,
            )
            updateUserDataUseCase(updated)
        }
    }

    private fun onCigsPerDayEdited(cigsPerDay: Int) {
        viewModelScope.launch {
            val current = viewStateFlow.value
            if (current is ProfileViewState.Data) {
                setViewState(
                    current.copy(
                        smokingDetails = current.smokingDetails.copy(cigarettesPerDay = cigsPerDay)
                    )
                )
            }
            val userData = getUserDataUseCase() ?: return@launch
            val updatedCigs = UserCigarettesData(
                cigarettesPerDay = cigsPerDay,
                packsPerDay = userData.cigarettesData.packsPerDay,
                priceData = userData.cigarettesData.priceData,
            )
            val updated = UserData(
                personalData = userData.personalData,
                cigarettesData = updatedCigs,
                quitData = userData.quitData,
            )
            updateUserDataUseCase(updated)
        }
    }

    private fun onPriceEdited(price: Float) {
        viewModelScope.launch {
            val current = viewStateFlow.value
            if (current is ProfileViewState.Data) {
                setViewState(
                    current.copy(
                        smokingDetails = current.smokingDetails.copy(price = price)
                    )
                )
            }
            val userData = getUserDataUseCase() ?: return@launch
            val updatedPriceData = UserCigarettesData.PriceData(
                price = price,
                currency = userData.cigarettesData.priceData.currency,
            )
            val updatedCigs = UserCigarettesData(
                cigarettesPerDay = userData.cigarettesData.cigarettesPerDay,
                packsPerDay = userData.cigarettesData.packsPerDay,
                priceData = updatedPriceData,
            )
            val updated = UserData(
                personalData = userData.personalData,
                cigarettesData = updatedCigs,
                quitData = userData.quitData,
            )
            updateUserDataUseCase(updated)
        }
    }

    private fun onDebugPanelClicked() {
        viewModelScope.launch {
            sendViewCommand(ProfileViewCommand.NavigateTo(AppScreen.DebugPanelScreen))
        }
    }
}
