package com.shahzadian.slipscreen.ui.slipcontext.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.slipscreen.ui.slipcontext.domain.GetSlipContextsUseCase
import com.shahzadian.slipscreen.ui.slipcontext.domain.SlipContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SlipContextViewModel @Inject constructor(
    private val getSlipContextsUseCase: GetSlipContextsUseCase,
) : BaseViewModel<SlipContextViewState, SlipContextViewEvent, SlipContextViewCommand>() {

    private var selectedContextId: String? = null

    override val initState: SlipContextViewState by lazyUnsafe {
        SlipContextViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: SlipContextViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val contexts = getSlipContextsUseCase()
                .map { it.toUiModel() }
            setViewState(
                SlipContextViewState.Data(
                    isSubmitButtonEnabled = selectedContextId != null,
                    selectedContextId = selectedContextId,
                    slipContexts = contexts
                )
            )
        }
    }

    private fun SlipContext.toUiModel(): SlipContextViewState.SlipContextUiModel {
        return SlipContextViewState.SlipContextUiModel(
            id = this.id,
            name = this.name,
            icon = this.icon,
        )
    }
}
