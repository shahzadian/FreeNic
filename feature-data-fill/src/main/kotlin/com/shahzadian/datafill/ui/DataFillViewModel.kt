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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DataFillViewModel @Inject constructor(
    private val fillableInfoHelper: FillableInfoHelper,
) :
    BaseViewModel<DataFillViewState, DataFillViewEvent, DataFillCommand>() {

    override val initState: DataFillViewState by lazyUnsafe {
        DataFillViewState.Loading
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

            DataFillViewEvent.OnSubmitNext -> {
                viewModelScope.launch {
                    fillableInfoHelper.getNextData()
                }
            }
        }
    }

    init {
        fillableInfoHelper.initialize(viewModelScope)

        fillableInfoHelper
            .currentInfoFlow
            .onEach { info ->
                setViewState(mapInfoToViewState(info))
            }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            fillableInfoHelper.getNextData()
        }
    }

    private fun mapInfoToViewState(info: FillableInfo): DataFillViewState {
        return when (info) {
            is InitScreenFillableInfo -> DataFillViewState.Init(
                pagesData = DataFillViewState.PagesData(
                    pagesCount = fillableInfoHelper.infoPagesCount,
                    currentPageIndex = fillableInfoHelper.currentPageIndex,
                )
            )

            is AboutScreenFillableInfo -> DataFillViewState.About(
                pagesData = DataFillViewState.PagesData(
                    pagesCount = fillableInfoHelper.infoPagesCount,
                    currentPageIndex = fillableInfoHelper.currentPageIndex,
                ),
                name = info.name,
                age = info.age?.toString()
            )

            else -> DataFillViewState.Loading
        }
    }
}