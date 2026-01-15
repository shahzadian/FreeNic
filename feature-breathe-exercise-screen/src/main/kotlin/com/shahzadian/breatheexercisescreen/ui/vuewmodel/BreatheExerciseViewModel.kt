package com.shahzadian.breatheexercisescreen.ui.vuewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.ad.AdHelper
import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreatheExerciseViewModel @Inject constructor(
    private val getBreatheExerciseSettingsUseCase: GetBreatheExerciseSettingsUseCase,
    private val adHelper: AdHelper,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<BreatheExerciseViewState, BreatheExerciseViewEvent, BreatheExerciseViewCommand>() {

    override val initState: BreatheExerciseViewState by lazyUnsafe {
        BreatheExerciseViewState.Loading
    }

    init {
        viewModelScope.launch {
            val settings = getBreatheExerciseSettingsUseCase()
            setViewState(
                BreatheExerciseViewState.Process(
                    inhaleSeconds = settings.inhaleSeconds,
                    holdSeconds = settings.holdSeconds,
                    exhaleSeconds = settings.exhaleSeconds,
                    cyclesCount = settings.cyclesCount,
                    currentCycle = 1,
                    helpingTexts = settings.helpingTexts.shuffled(),
                    exerciseState = BreatheExerciseViewState.ExerciseState.READY_FOR_EXERCISE,
                )
            )
        }
    }

    override fun onViewEventHappened(event: BreatheExerciseViewEvent) {
        when (event) {
            is BreatheExerciseViewEvent.OnCircleClick -> {
                startExercise(1)
            }

            is BreatheExerciseViewEvent.OnInhaleFinish -> {
                onInhaleFinished()
            }

            is BreatheExerciseViewEvent.OnHoldFinish -> {
                onHoldFinished()
            }

            is BreatheExerciseViewEvent.OnExhaleFinish -> {
                onExhaleFinished()
            }

            is BreatheExerciseViewEvent.OnFinishClick -> {
                onFinishClicked()
            }
        }
    }

    private fun startExercise(cycle: Int) {
        (viewState as? BreatheExerciseViewState.Process)?.let { state ->
            viewModelScope.launch {
                analyticsEventSender.send(
                    AnalyticsEvent(
                        name = AnalyticsEventConst.BREATHE_EXERCISE_START_TAP
                    )
                )
                setViewState(
                    state.copy(
                        currentCycle = cycle,
                        exerciseState = BreatheExerciseViewState.ExerciseState.INHALE,
                    ),
                )
            }
        }
    }

    private fun onInhaleFinished() {
        (viewState as? BreatheExerciseViewState.Process)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    state.copy(
                        exerciseState = BreatheExerciseViewState.ExerciseState.HOLD,
                    ),
                )
            }
        }
    }

    private fun onHoldFinished() {
        (viewState as? BreatheExerciseViewState.Process)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    state.copy(
                        exerciseState = BreatheExerciseViewState.ExerciseState.EXHALE,
                    ),
                )
            }
        }
    }

    private fun onExhaleFinished() {
        (viewState as? BreatheExerciseViewState.Process)?.let { state ->
            if (state.currentCycle < state.cyclesCount) {
                startExercise(state.currentCycle + 1)
            }
        }
    }

    private fun onFinishClicked() {
        viewModelScope.launch {
            sendFinishAnalyticsEvent()
            adHelper.requestAdShow()
            sendViewCommand(BreatheExerciseViewCommand.NavigateBack)
        }
    }

    private fun sendFinishAnalyticsEvent() {
        val state = (viewState as? BreatheExerciseViewState.Process) ?: return
        val finishedStepState = when (state.exerciseState) {
            BreatheExerciseViewState.ExerciseState.INHALE -> AnalyticsEventConst.PARAM_VALUE_FINISHED_STEP_STATE_INHALE
            BreatheExerciseViewState.ExerciseState.HOLD -> AnalyticsEventConst.PARAM_VALUE_FINISHED_STEP_STATE_HOLD
            BreatheExerciseViewState.ExerciseState.EXHALE -> AnalyticsEventConst.PARAM_VALUE_FINISHED_STEP_STATE_EXHALE
            BreatheExerciseViewState.ExerciseState.READY_FOR_EXERCISE -> AnalyticsEventConst.PARAM_VALUE_FINISHED_STEP_STATE_READY_FOR_EXERCISE
        }
        analyticsEventSender.send(
            AnalyticsEvent(
                name = AnalyticsEventConst.BREATHE_EXERCISE_FINISH_TAP,
                params = mapOf(
                    AnalyticsEventConst.PARAM_FINISHED_STEP_STATE to finishedStepState.asAnalyticsProperty()
                )
            )
        )
    }
}
