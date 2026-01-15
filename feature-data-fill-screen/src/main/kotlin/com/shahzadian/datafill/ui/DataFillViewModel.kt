package com.shahzadian.datafill.ui

import android.text.format.DateUtils
import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
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
import com.shahzadian.datafill.domain.fillableInfo.result.ResultFillableInfo
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import com.shahzadian.user.model.QuitData
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DataFillViewModel @Inject constructor(
    private val fillableInfoHelper: FillableInfoHelper,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
) : BaseViewModel<DataFillViewState, DataFillViewEvent, DataFillCommand>() {

    override val initState: DataFillViewState by lazyUnsafe {
        DataFillViewState.Loading
    }

    override fun onViewEventHappened(event: DataFillViewEvent) {
        when (event) {
            is DataFillViewEvent.OnNameChanged -> {
                fillableInfoHelper.sendEvent(
                    AboutScreenFillableInfoEvent.OnNameChanged(event.name)
                )
            }

            is DataFillViewEvent.OnAgeChanged -> {
                fillableInfoHelper.sendEvent(
                    AboutScreenFillableInfoEvent.OnAgeChanged(event.age.toIntOrNull())
                )
            }

            DataFillViewEvent.OnSubmitNext -> {
                viewModelScope.launch {
                    fillableInfoHelper.submitAndGetNext() ?: onAllDataFilled()
                }
            }

            is DataFillViewEvent.OnCigarettesPerDayChanged -> {
                fillableInfoHelper.sendEvent(
                    CigarettesPerDayFillableInfoEvent.OnCigarettesPerDayChanged(event.cigarettesPerDay)
                )
            }


            is DataFillViewEvent.OnPackPriceChanged -> {
                fillableInfoHelper.sendEvent(
                    PackPriceFillableInfoEvent.OnPriceChanged(
                        price = event.price,
                    )
                )
            }

            is DataFillViewEvent.OnQuitDateChanged -> {
                fillableInfoHelper.sendEvent(
                    PackPriceFillableInfoEvent.OnQuitDateChanged(
                        quitDateMillis = event.quitDateMillis,
                    )
                )
            }

            is DataFillViewEvent.OnBack -> {
                viewModelScope.launch {
                    fillableInfoHelper.getPreviousData()
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
            fillableInfoHelper.submitAndGetNext()
        }
    }

    private fun onAllDataFilled() {
        viewModelScope.launch {
            fillableInfoHelper.sharedState.toSaveableUserData()?.let {
                val analyticsEvent = createAnalyticsEvent(it)
                analyticsEventSender.send(analyticsEvent)
                updateUserDataUseCase(it)
                updateUserDataUseCase.setUserRegistered(true)
            }
            sendViewCommand(DataFillCommand.NavigateTo(AppScreen.MainScreen))
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
                quitData = DataFillViewState.PackPrice.QuitData(
                    quitDateMillis = info.quitDateMillis,
                    displayableQuitDate = info.displayableQuitDate,

                    ),
                price = DataFillViewState.PackPrice.PriceData(
                    price = info.price?.toString() ?: "",
                    currency = info.currency,
                ),
            )

            is ResultFillableInfo -> DataFillViewState.Result(
                pagesData = DataFillViewState.PagesData(
                    pagesCount = fillableInfoHelper.infoPagesCount,
                    currentPageIndex = fillableInfoHelper.currentPageIndex,
                ),
                userData = info.userInfo.toUserData()
            )

            else -> DataFillViewState.Loading
        }
    }

    private fun ResultFillableInfo.UserInfo.toUserData(): DataFillViewState.Result.UserData {
        return DataFillViewState.Result.UserData(
            name = this.name,
            age = this.age.toString(),
            cigarettesPerDay = this.cigarettesPerDay.toString()
        )
    }

    private fun FillableInfoSharedState.toSaveableUserData(): UserData? {
        val userName = this.name ?: return null
        val userAge = this.age ?: return null
        val userCigarettesPerDay = this.cigarettesPerDay ?: return null
        val packsPerDay = this.packsPerDay ?: return null
        val price = this.packPrice ?: return null
        val currency = this.currency ?: return null
        val quitDate = this.quitDate ?: return null

        return UserData(
            personalData = UserPersonalData(
                name = userName,
                age = userAge,
            ),
            cigarettesData = UserCigarettesData(
                cigarettesPerDay = userCigarettesPerDay,
                packsPerDay = packsPerDay,
                priceData = UserCigarettesData.PriceData(
                    price = price,
                    currency = currency,
                ),
            ),
            quitData = QuitData(
                quitDateMillis = quitDate,
            )
        )
    }

    private fun createAnalyticsEvent(userData: UserData): AnalyticsEvent {
        return AnalyticsEvent(
            name = AnalyticsEvent.EventNames.EVENT_USER_REGISTERED,
            params = mapOf(
                AnalyticsEvent.EventParams.PARAM_AGE to userData.personalData.age.asAnalyticsProperty(),
                AnalyticsEvent.EventParams.PARAM_IS_QUIT_TODAY to DateUtils.isToday(userData.quitData.quitDateMillis)
                    .asAnalyticsProperty(),
                AnalyticsEvent.EventParams.PARAM_CIGS_PER_DAY to userData.cigarettesData.cigarettesPerDay.asAnalyticsProperty(),
                AnalyticsEvent.EventParams.PARAM_PACK_PRICE to userData.cigarettesData.priceData.price.asAnalyticsProperty(),
            )
        )
    }
}
