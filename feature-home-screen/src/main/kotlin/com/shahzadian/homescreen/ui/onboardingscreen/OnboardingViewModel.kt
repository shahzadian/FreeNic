package com.shahzadian.homescreen.ui.onboardingscreen

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.homescreen.ui.welcomescreen.domain.GetOnboardingDataUseCase
import com.shahzadian.homescreen.ui.welcomescreen.domain.OnboardingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val getOnboardingDataUseCase: GetOnboardingDataUseCase,
) : BaseViewModel<OnboardingViewState, OnboardingViewEvent, OnboardingViewCommand>() {

    override val initState: OnboardingViewState by lazyUnsafe { OnboardingViewState.Loading }

    init {
        updateData()
    }

    override fun onViewEventHappened(event: OnboardingViewEvent) {
        when (event) {
            is OnboardingViewEvent.OnSubmitButtonClicked -> {
                updateData()
            }
        }
    }

    private fun updateData() {
        viewModelScope
            .launch {
                getOnboardingDataUseCase.getNextData()
                    ?.let { data ->
                        setViewState(data.toViewState())
                    }
                    ?: navigateToPersonalDataFillScreen()
            }
    }

    private suspend fun navigateToPersonalDataFillScreen() {
        sendViewCommand(
            OnboardingViewCommand.NavigateTo(
                screen = AppScreen.DataFillScreen
            )
        )
    }

    private fun OnboardingData.toViewState(): OnboardingViewState.Data {
        return OnboardingViewState.Data(
            title = this.title,
            subtitle = this.subtitle,
            image = this.image,
            buttonText = this.submitText,
        )
    }
}
