package com.shahzadian.distractionactivityscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.distractionactivityscreen.domain.GetDistractionActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DistractionActivityViewModel @Inject constructor(
    private val getDistractionActivitiesUseCase: GetDistractionActivitiesUseCase,
) : BaseViewModel<DistractionActivityViewState, DistractionActivityViewEvent, DistractionActivityViewCommand>() {

    override val initState: DistractionActivityViewState by lazyUnsafe { DistractionActivityViewState.Loading }

    init {
        getDistractionActivities()
    }

    override fun onViewEventHappened(event: DistractionActivityViewEvent) {
//
    }

    private fun getDistractionActivities() {
        viewModelScope.launch {
            val activities = getDistractionActivitiesUseCase()
            activities.firstOrNull()?.let { setViewState(DistractionActivityViewState.Data(it)) }
        }
    }
}
