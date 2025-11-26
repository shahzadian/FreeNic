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
//
    }
}
