package com.shahzadian.cravingsscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.cravingsscreen.domain.CravingTool
import com.shahzadian.cravingsscreen.domain.GetCravingToolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CravingsViewModel @Inject constructor(
    private val getCravingToolsUseCase: GetCravingToolsUseCase,
) : BaseViewModel<CravingsViewState, CravingsViewEvent, CravingsViewCommand>() {

    override val initState: CravingsViewState by lazyUnsafe {
        CravingsViewState.Loading
    }

    init {
        viewModelScope.launch {
            val tools = getCravingToolsUseCase()
            setViewState(tools.toViewState())
        }
    }

    override fun onViewEventHappened(event: CravingsViewEvent) {
//
    }

    private fun List<CravingTool>.toViewState(): CravingsViewState.Data {
        return CravingsViewState.Data(
            tools = this.take(3).map { tool ->
                CravingsViewState.Data.CravingsToolUiModel(
                    title = tool.name,
                    subtitle = tool.description,
                    icon = tool.icon,
                    onClick = {},
                )
            },
            showAllButton = this.size > 3,
            onAllButtonClick = {}
        )
    }
}
