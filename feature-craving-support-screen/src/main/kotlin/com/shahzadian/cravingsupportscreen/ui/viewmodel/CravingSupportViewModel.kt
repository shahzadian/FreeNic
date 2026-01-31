package com.shahzadian.cravingsupportscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CravingSupportViewModel @Inject constructor(
    private val getMotivationalNotesUseCase: GetMotivationalNotesUseCase,
) : BaseViewModel<CravingSupportViewState, CravingSupportViewEvent, CravingSupportViewCommand>() {

    override val initState: CravingSupportViewState by lazyUnsafe {
        CravingSupportViewState.Loading
    }

    private var _motivationalNote: MotivationalNote? = null
    private var selectedCravingStrength: CravingSupportViewState.CravingStrength? = null

    init {
        setData()
    }

    override fun onViewEventHappened(event: CravingSupportViewEvent) {
        when (event) {
            is CravingSupportViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }

            is CravingSupportViewEvent.OnCravingStrengthSelected -> {
                onCravingStrengthSelected(event.strength)
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val motivationalNotes = getMotivationalNote()
            setViewState(
                CravingSupportViewState.Data(
                    motivationalNote = motivationalNotes.text,
                    selectedCravingStrength = selectedCravingStrength,
                )
            )
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(CravingSupportViewCommand.NavigateBack)
        }
    }

    private fun onCravingStrengthSelected(strength: CravingSupportViewState.CravingStrength) {
        selectedCravingStrength = strength
        setData()
    }

    private suspend fun getMotivationalNote(): MotivationalNote {
        return _motivationalNote ?: let {
            val notes = getMotivationalNotesUseCase()
            _motivationalNote = notes.random()
            return@let _motivationalNote!!
        }
    }
}
