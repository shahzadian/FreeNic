package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import android.text.format.DateUtils
import androidx.lifecycle.viewModelScope
import com.freenic.analytics.AnalyticsEvent
import com.freenic.analytics.AnalyticsEventConst
import com.freenic.analytics.AnalyticsEventSender
import com.freenic.analytics.asAnalyticsProperty
import com.freenic.ui.AppScreen
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.slip.slipcase.domain.CreateSlipCaseUseCase
import com.shahzadian.slip.slipcase.domain.SlipCase
import com.shahzadian.slip.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slip.slipreason.domain.SlipReason
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class SlipReasonViewModel @Inject constructor(
    private val getSlipReasonsUseCase: GetSlipReasonsUseCase,
    private val createSlipCaseUseCase: CreateSlipCaseUseCase,
    private val analyticsEventSender: AnalyticsEventSender,
    private val getUserDataUseCase: GetUserDataUseCase,
) : BaseViewModel<SlipReasonViewState, SlipReasonViewEvent, SlipReasonViewCommand>() {

    private var selectedReasonId: String? = null
    private var slipDate: Long = System.currentTimeMillis()

    override val initState: SlipReasonViewState by lazyUnsafe {
        SlipReasonViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: SlipReasonViewEvent) {
        when (event) {
            is SlipReasonViewEvent.OnReasonClicked -> {
                selectedReasonId = event.reasonId
                updateData()
            }

            is SlipReasonViewEvent.OnConfirmClicked -> {
                onConfirmClicked()
            }

            is SlipReasonViewEvent.OnSkipClicked -> {
                onSkipClicked()
            }

            is SlipReasonViewEvent.OnSlipDatePicked -> {
                onSlipDatePicked(event.millis)
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val reasons = getSlipReasonsUseCase()
                .map { it.toUiModel() }
            val quitDate = getUserDataUseCase()?.quitData?.quitDateMillis
                ?: throw IllegalStateException("Quit data is null")
            setViewState(
                SlipReasonViewState.Data(
                    isSubmitButtonEnabled = selectedReasonId != null,
                    selectedReasonId = selectedReasonId,
                    slipReasons = reasons,
                    slipDateUiModel = SlipReasonViewState.SlipDateUiModel(
                        millis = slipDate,
                        minimalSlipDateMillis = quitDate,
                        displayableDate = getDisplayableSlipDate()
                    )
                )
            )
        }
    }

    private fun updateData() {
        viewModelScope.launch {
            val reasons = (viewState as? SlipReasonViewState.Data)?.slipReasons
                ?: getSlipReasonsUseCase().map { it.toUiModel() }
            val quitDate = getUserDataUseCase()?.quitData?.quitDateMillis
                ?: throw IllegalStateException("Quit data is null")
            setViewState(
                SlipReasonViewState.Data(
                    isSubmitButtonEnabled = selectedReasonId != null,
                    selectedReasonId = selectedReasonId,
                    slipReasons = reasons,
                    slipDateUiModel = SlipReasonViewState.SlipDateUiModel(
                        millis = slipDate,
                        minimalSlipDateMillis = quitDate,
                        displayableDate = getDisplayableSlipDate()
                    )
                )
            )
        }
    }

    private fun onConfirmClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SLIP_REASON_CONFIRM_TAP,
                    params = mapOf(
                        AnalyticsEventConst.SLIP_REASON_CONFIRM_PARAM_REASON_ID to requireNotNull(
                            selectedReasonId
                        ) {
                            "Selected reason id is null."
                        }.asAnalyticsProperty(),
                        AnalyticsEventConst.SLIP_REASON_CONFIRM_PARAM_WAS_SLIP_TODAY to DateUtils.isToday(
                            slipDate
                        )
                            .asAnalyticsProperty()
                    )
                )
            )
            createSlipCaseUseCase(
                slipCase = SlipCase(
                    reasonId = requireNotNull(selectedReasonId) {
                        "Selected reason id is null."
                    },
                    slipDateMillis = slipDate,
                )
            )
            sendViewCommand(SlipReasonViewCommand.NavigateTo(AppScreen.SlipResultScreen))
        }
    }

    private fun onSkipClicked() {
        viewModelScope.launch {
            analyticsEventSender.send(
                AnalyticsEvent(
                    name = AnalyticsEventConst.SLIP_REASON_SKIP_TAP,
                )
            )
            sendViewCommand(SlipReasonViewCommand.NavigateTo(AppScreen.SlipResultScreen))
        }
    }

    private fun onSlipDatePicked(millis: Long) {
        slipDate = millis
        updateData()
    }

    private fun SlipReason.toUiModel(): SlipReasonViewState.SlipReasonUiModel {
        return SlipReasonViewState.SlipReasonUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon,
        )
    }

    private fun extractDisplayableQuitDate(quitDateMillis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return Instant.ofEpochMilli(quitDateMillis)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }

    private fun getDisplayableSlipDate(): TextWrapper {
        val isToday = DateUtils.isToday(slipDate)
        return if (isToday) {
            TextWrapper.FromRes(R.string.slip_reason_date_today)
        } else {
            TextWrapper.FromString(extractDisplayableQuitDate(slipDate))
        }
    }
}
