package com.shahzadian.dashboard.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.cravingsscreen.domain.CravingTool
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCase
import com.shahzadian.dashboard.domain.GetUserDashboardUseCase
import com.shahzadian.dashboard.domain.UserDashboard
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import com.shahzadian.recoverycalculator.domain.HealthRecoveryCalculator
import com.shahzadian.recoverycalculator.domain.factory.HealthRecoveryCalculatorFactory
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
    private val getCravingToolsUseCase: GetCravingToolsUseCase,
    private val recoveryCalculatorFactory: HealthRecoveryCalculatorFactory,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
    private val getMotivationalNotesUseCase: GetMotivationalNotesUseCase,
) : BaseViewModel<DashboardViewState, DashboardViewEvent, DashboardViewCommand>() {

    private var recoveryCalculator: HealthRecoveryCalculator? = null

    init {
        setData()
    }

    override val initState: DashboardViewState by lazyUnsafe {
        DashboardViewState.Loading
    }

    override fun onViewEventHappened(event: DashboardViewEvent) {
        when (event) {
            is DashboardViewEvent.OnCravingsHelpClicked -> {
                onCravingsHelpClicked()
            }

            is DashboardViewEvent.OnDetailedInsightsClicked -> {
                onDetailedInsightsClicked()
            }

            is DashboardViewEvent.OnMotivationalNoteRefreshClicked -> {
                onHelpfulNoteRefreshClicked()
            }

            is DashboardViewEvent.OnMotivationalNoteAddClicked -> {
                onHelpfulNoteAddClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            recoveryCalculator = recoveryCalculatorFactory.create()
            val dashboard = getUserDashboardUseCase()
                ?: throw IllegalStateException("User dashboard is null")
            setViewState(
                state = DashboardViewState.Data(
                    mainProgressUiModel = dashboard.toMainProgress(),
                    healthInsightsUiModel = recoveryCalculator!!.getRandomHealthInsight(),
                    motivationalNote = getMotivationalNotesUseCase().random().toUiModel(),
                )
            )
        }
    }

    private fun onHelpfulNoteRefreshClicked() {
        val state = viewState as? DashboardViewState.Data ?: return
        viewModelScope.launch {
            setViewState(
                state.copy(
                    motivationalNote = getMotivationalNotesUseCase().random().toUiModel(),
                )
            )
        }
    }

    private fun UserDashboard.toMainProgress(): DashboardViewState.MainProgressUiModel {
        return DashboardViewState.MainProgressUiModel(
            noCigDays = this.mainProgressData.noCigDays,
            savedMoney = this.mainProgressData.savedMoney,
            currency = this.mainProgressData.currency,
        )
    }

    private fun onCravingsHelpClicked() {
        viewModelScope.launch {
            val cravingTools = getCravingToolsUseCase()
            val screen = cravingTools
                .mapNotNull { (it.behavior as? CravingTool.Behavior.NavigateToScreen)?.screen }
                .shuffled()
                .firstOrNull() ?: return@launch
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = screen,
                )
            )
        }
    }

    private fun HealthRecoveryCalculator.getRandomHealthInsight(): DashboardViewState.HealthInsightsUiModel {
        return listOf(
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_oxygen_title),
                progress = calculateOxygenCoNormalizationPercent()
            ),
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_circulation_title),
                progress = calculateCirculationHeartRecoveryPercent()
            ),
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_lung_self_cleaning_title),
                progress = calculateLungSelfCleaningPercent()
            ),
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_energy_level_title),
                progress = calculateEnergyLevelPercent()
            ),
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_breathing_comfort_title),
                progress = calculateBreathingComfortPercent()
            ),
            DashboardViewState.HealthInsightsUiModel(
                label = TextWrapper.FromRes(R.string.insights_endurance_title),
                progress = calculatePhysicalEndurancePercent(),
            ),
        ).random()
    }

    private fun onDetailedInsightsClicked() {
        viewModelScope.launch {
            val subscription = getUserCurrentSubscriptionUseCase()
            val screen = if (subscription != null) {
                AppScreen.InsightsScreen
            } else {
                AppScreen.PaywallScreen
            }
            sendViewCommand(
                DashboardViewCommand.NavigateTo(
                    screen = screen,
                )
            )
        }
    }

    private fun MotivationalNote.toUiModel(): DashboardViewState.MotivationalNoteUiModel {
        return DashboardViewState.MotivationalNoteUiModel(
            text = this.text,
        )
    }

    private fun onHelpfulNoteAddClicked() {
        viewModelScope.launch {
            val hasPaidSubscription = getUserCurrentSubscriptionUseCase() != null
            if (!hasPaidSubscription) {
                sendViewCommand(
                    DashboardViewCommand.NavigateTo(
                        screen = AppScreen.PaywallScreen,
                    )
                )
            } else {
//                TODO notes screen
            }
        }
    }
}
