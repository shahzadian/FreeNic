package com.shahzadian.profilescreen.ui.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.freenic.activityevent.ActivityEvent
import com.freenic.activityevent.ActivityEventSender
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.domain.ClearAppDataUseCase
import com.freenic.ui.AppScreen
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.extractMainDisplayableDate
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.SubscriptionPlan
import com.shahzadian.subscription.domain.UserSubscriptionStatus
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
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val clearAppDataUseCase: ClearAppDataUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val activityEventSender: ActivityEventSender,
) : BaseViewModel<ProfileViewState, ProfileViewEvent, ProfileViewCommand>() {

    override val initState: ProfileViewState by lazyUnsafe { ProfileViewState.Loading }

    init {
        updateProfileData()
    }

    override fun onViewEventHappened(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.OnUpgradeSubscriptionClicked -> {
                viewModelScope.launch {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.PROFILE_SUBSCRIPTION_UPGRADE_TAP,
                        )
                    )
                    sendViewCommand(ProfileViewCommand.NavigateTo(AppScreen.PaywallScreen))
                }
            }

            is ProfileViewEvent.OnManageSubscriptionClicked -> {
                viewModelScope.launch {
                    analyticsEventSender.send(
                        AnalyticsEvent(
                            name = AnalyticsEventConst.PROFILE_SUBSCRIPTION_MANAGE_TAP,
                        )
                    )
                    sendViewCommand(ProfileViewCommand.NavigateTo(AppScreen.ManageSubscriptionScreen))
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
            is ProfileViewEvent.OnClearAllDataConfirmed -> onClearAllDataConfirmed()
            is ProfileViewEvent.OnPrivacyClicked -> onPrivacyClicked()
            is ProfileViewEvent.OnTermsClicked -> onTermsClicked()
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
            val subscriptionStatus =
                getUserCurrentSubscriptionUseCase()
            setViewState(
                ProfileViewState.Data(
                    userNameLetter = userNameLetter,
                    appVersion = displayableVersion,
                    subscriptionState = when (subscriptionStatus) {
                        is UserSubscriptionStatus.NoSubscription -> {
                            ProfileViewState.Data.SubscriptionState.NoSubscription
                        }

                        is UserSubscriptionStatus.HasSubscription -> {
                            ProfileViewState.Data.SubscriptionState.HasSubscription(
                                title = getSubscriptionTitle(
                                    plan = subscriptionStatus.plan
                                ),
                                activeUntil = extractMainDisplayableDate(subscriptionStatus.expiresAtMillis)
                            )
                        }
                    },
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
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.PROFILE_QUIT_DATE_EDITED,
                )
            )
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

    private fun onClearAllDataConfirmed() {
        viewModelScope
            .launch {
                analyticsEventSender.send(
                    AnalyticsEvent(
                        name = AnalyticsEventConst.PROFILE_CLEAR_ALL_DATA_CONFIRMATION,
                    )
                )
                clearAppDataUseCase()
                sendViewCommand(
                    command = ProfileViewCommand.NavigateTo(
                        screen = AppScreen.WelcomeScreen,
                        clearFromStack = true,
                    ),
                )
            }
    }

    private fun onPrivacyClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.PROFILE_PRIVACY_POLICY_TAP
                )
            )
            activityEventSender.sendEvent(ActivityEvent.PrivacyPolicyOpenRequest)
        }
    }

    private fun onTermsClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.PROFILE_TERMS_OF_SERVICES_TAP
                )
            )
            activityEventSender.sendEvent(ActivityEvent.TermsOfServicesOpenRequest)
        }
    }

    private fun getSubscriptionTitle(plan: SubscriptionPlan): TextWrapper {
        return when (plan.paymentFrequency) {
            SubscriptionPlan.PaymentFrequency.MONTHLY -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_monthly)
            }

            SubscriptionPlan.PaymentFrequency.ANNUAL -> {
                TextWrapper.FromRes(R.string.subscription_plan_name_yearly)
            }
        }
    }
}
