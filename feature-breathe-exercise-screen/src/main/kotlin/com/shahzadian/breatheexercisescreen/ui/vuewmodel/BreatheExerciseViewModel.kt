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
                BreatheExerciseViewState.Data.ReadyForExercise(
                    inhaleSeconds = settings.inhaleSeconds,
                    holdSeconds = settings.holdSeconds,
                    exhaleSeconds = settings.exhaleSeconds,
                    cyclesCount = settings.cyclesCount,
                    currentCycle = 1,
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
        }
    }

    private fun startExercise(cycle: Int) {
        (viewState as? BreatheExerciseViewState.Data)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    BreatheExerciseViewState.Data.Inhale(
                        inhaleSeconds = state.inhaleSeconds,
                        holdSeconds = state.holdSeconds,
                        exhaleSeconds = state.exhaleSeconds,
                        cyclesCount = state.cyclesCount,
                        currentCycle = cycle,
                    )
                )
            }
        }
    }

    private fun onInhaleFinished() {
        (viewState as? BreatheExerciseViewState.Data)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    BreatheExerciseViewState.Data.Hold(
                        inhaleSeconds = state.inhaleSeconds,
                        holdSeconds = state.holdSeconds,
                        exhaleSeconds = state.exhaleSeconds,
                        cyclesCount = state.cyclesCount,
                        currentCycle = state.currentCycle + 1,
                    )
                )
            }
        }
    }

    private fun onHoldFinished() {
        (viewState as? BreatheExerciseViewState.Data)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    BreatheExerciseViewState.Data.Exhale(
                        inhaleSeconds = state.inhaleSeconds,
                        holdSeconds = state.holdSeconds,
                        exhaleSeconds = state.exhaleSeconds,
                        cyclesCount = state.cyclesCount,
                        currentCycle = state.currentCycle,
                    )
                )
            }
        }
    }

    private fun onExhaleFinished() {
        (viewState as? BreatheExerciseViewState.Data)?.let { state ->
            if (state.currentCycle < state.cyclesCount) {
                startExercise(state.currentCycle + 1)
            }
        }
    }
}
