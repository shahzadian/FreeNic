package com.shahzadian.datafill.ui

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.datafill.domain.FillableInfoHelper
import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfoEvent
import com.shahzadian.datafill.domain.fillableInfo.cigarettesperday.CigarettesPerDayFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.cigarettesperday.CigarettesPerDayFillableInfoEvent
import com.shahzadian.datafill.domain.fillableInfo.initinfo.InitScreenFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.packprice.PackPriceFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.packprice.PackPriceFillableInfoEvent
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

            is DataFillViewEvent.OnCigarettesPerDayChanged ->
                fillableInfoHelper.sendEvent(
                    CigarettesPerDayFillableInfoEvent.OnCigarettesPerDayChanged(event.cigarettesPerDay)
                )

            is DataFillViewEvent.OnPackPriceChanged ->
                fillableInfoHelper.sendEvent(
                    PackPriceFillableInfoEvent.OnPriceChanged(
                        price = event.price,
                        currency = event.currency
                    )
                )
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
                submitButtonEnabled = info.isValid(),
                name = info.name,
                age = info.age?.toString()
            )

            is CigarettesPerDayFillableInfo -> DataFillViewState.CigarettesPerDay(
                pagesData = DataFillViewState.PagesData(
                    pagesCount = fillableInfoHelper.infoPagesCount,
                    currentPageIndex = fillableInfoHelper.currentPageIndex,
                ),
                submitButtonEnabled = info.isValid(),
                cigarettesCountIntRange = info.cigarettesCountIntRange,
                cigarettesPerDay = info.cigarettesPerDay.toString(),
                packsPerDay = info.packsPerDay.toString(),
            )

            is PackPriceFillableInfo -> DataFillViewState.PackPrice(
                pagesData = DataFillViewState.PagesData(
                    pagesCount = fillableInfoHelper.infoPagesCount,
                    currentPageIndex = fillableInfoHelper.currentPageIndex,
                ),
                submitButtonEnabled = info.isValid(),
                price = DataFillViewState.PackPrice.PriceData(
                    price = info.price?.toString() ?: "",
                    currency = info.currency,
                    currencies = info.currencies,
                ),
            )

            else -> DataFillViewState.Loading
        }
    }
}
