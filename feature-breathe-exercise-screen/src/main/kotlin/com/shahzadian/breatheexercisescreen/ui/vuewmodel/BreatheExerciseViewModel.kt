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
                )
            )
        }
    }

    override fun onViewEventHappened(event: BreatheExerciseViewEvent) {
        when (event) {
            is BreatheExerciseViewEvent.OnCircleClick -> {
                onStartClicked()
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

    private fun onStartClicked() {
        (viewState as? BreatheExerciseViewState.Data)?.let { state ->
            viewModelScope.launch {
                setViewState(
                    BreatheExerciseViewState.Data.Inhale(
                        inhaleSeconds = state.inhaleSeconds,
                        holdSeconds = state.holdSeconds,
                        exhaleSeconds = state.exhaleSeconds,
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
                    )
                )
            }
        }
    }

    private fun onExhaleFinished() {

    }
}
