package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import android.text.format.DateUtils
import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.slipscreen.ui.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slipscreen.ui.slipreason.domain.SlipReason
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

            is SlipReasonViewEvent.OnConfirmClicked -> {}

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
            setViewState(
                SlipReasonViewState.Data(
                    isSubmitButtonEnabled = selectedReasonId != null,
                    selectedReasonId = selectedReasonId,
                    slipReasons = reasons,
                    slipDateUiModel = SlipReasonViewState.SlipDateUiModel(
                        millis = slipDate,
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
            setViewState(
                SlipReasonViewState.Data(
                    isSubmitButtonEnabled = selectedReasonId != null,
                    selectedReasonId = selectedReasonId,
                    slipReasons = reasons,
                    slipDateUiModel = SlipReasonViewState.SlipDateUiModel(
                        millis = slipDate,
                        displayableDate = getDisplayableSlipDate()
                    )
                )
            )
        }
    }

    private fun onSkipClicked() {
        viewModelScope.launch {
            sendViewCommand(SlipReasonViewCommand.NavigateBack)
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
