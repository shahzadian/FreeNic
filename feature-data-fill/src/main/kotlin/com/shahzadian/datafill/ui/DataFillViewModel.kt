package com.shahzadian.datafill.ui

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.datafill.domain.FillableInfoHelper
import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfoEvent
import com.shahzadian.datafill.domain.fillableInfo.initinfo.InitScreenFillableInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DataFillViewModel @Inject constructor(
    private val fillableInfoHelper: FillableInfoHelper,
) :
    BaseViewModel<DataFillViewState, DataFillViewEvent, DataFillCommand>() {

    // Initialize with Init state; real state will be loaded from providers
    override val initState: DataFillViewState by lazyUnsafe {
        DataFillViewState.Init
    }

    override fun onViewEventHappened(event: DataFillViewEvent) {
        when (event) {
            is DataFillViewEvent.OnNameChanged ->
                fillableInfoHelper.sendEvent(
                    AboutScreenFillableInfoEvent.OnNameChanged(event.name)
                )

            is DataFillViewEvent.OnAgeChanged ->
                fillableInfoHelper.sendEvent(
                    AboutScreenFillableInfoEvent.OnAgeChanged(event.age.toIntOrNull())
                )
        }
    }

    init {
        // Initialize domain helper and request the first page
        fillableInfoHelper.initialize(viewModelScope)

        viewModelScope.launch {
            // Move to the first provider
            fillableInfoHelper.getNextData()
            // Observe provider's info and map to UI state
            fillableInfoHelper.currentInfoFlow?.collect { info ->
                setViewState(mapInfoToViewState(info))
            }
        }
    }

    private fun mapInfoToViewState(info: FillableInfo): DataFillViewState {
        return when (info) {
            is InitScreenFillableInfo -> DataFillViewState.Init
            is AboutScreenFillableInfo -> DataFillViewState.About(
                name = info.name,
                age = info.age?.toString()
            )

            else -> DataFillViewState.Init
        }
    }
}