package com.shahzadian.breatheexercisescreen.ui.vuewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreatheExerciseViewModel @Inject constructor(
    private val getBreatheExerciseSettingsUseCase: GetBreatheExerciseSettingsUseCase,
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
                    helpingTexts = settings.helpingTexts,
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
                viewModelScope.launch {
                    sendViewCommand(BreatheExerciseViewCommand.NavigateBack)
                }
            }
        }
    }

    private fun startExercise(cycle: Int) {
        (viewState as? BreatheExerciseViewState.Process)?.let { state ->
            viewModelScope.launch {
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
}
