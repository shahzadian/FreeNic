package com.shahzadian.slipscreen.ui.slipreason.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.slipscreen.ui.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slipscreen.ui.slipreason.domain.SlipReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SlipReasonViewModel @Inject constructor(
    private val getSlipReasonsUseCase: GetSlipReasonsUseCase,
) : BaseViewModel<SlipReasonViewState, SlipReasonViewEvent, SlipReasonViewCommand>() {

    private var selectedReasonId: String? = null

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
                    slipReasons = reasons
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
                    slipReasons = reasons
                )
            )
        }
    }

    private fun onSkipClicked() {
        viewModelScope.launch {
            sendViewCommand(SlipReasonViewCommand.NavigateBack)
        }
    }

    private fun SlipReason.toUiModel(): SlipReasonViewState.SlipReasonUiModel {
        return SlipReasonViewState.SlipReasonUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon,
        )
    }
}
