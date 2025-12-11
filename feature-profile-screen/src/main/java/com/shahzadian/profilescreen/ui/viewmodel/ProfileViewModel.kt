package com.shahzadian.profilescreen.ui.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.baseui.R
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.QuitData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getUserDataUseCase: GetUserDataUseCase,
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
