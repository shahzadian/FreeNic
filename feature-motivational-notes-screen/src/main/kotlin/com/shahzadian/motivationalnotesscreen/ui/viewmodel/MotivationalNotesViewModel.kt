package com.shahzadian.motivationalnotesscreen.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MotivationalNotesViewModel @Inject constructor(
    private val getMotivationalNotesUseCase: GetMotivationalNotesUseCase,
) : BaseViewModel<MotivationalNotesViewState, MotivationalNotesViewEvent, MotivationalNotesViewCommand>() {

    override val initState: MotivationalNotesViewState by lazyUnsafe {
        MotivationalNotesViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: MotivationalNotesViewEvent) {

    }

    private fun setData() {
        viewModelScope.launch {
            val notes = getMotivationalNotesUseCase()
                .map { it.toUiModel() }
            setViewState(
                MotivationalNotesViewState.Data(
                    notes = notes,
                )
            )
        }
    }

    private fun MotivationalNote.toUiModel(): MotivationalNotesViewState.MotivationalNoteUiModel {
        return MotivationalNotesViewState.MotivationalNoteUiModel(
            text = this.text,
            source = when (source) {
                is MotivationalNote.Source.General -> MotivationalNotesViewState.MotivationalNoteSourceUiModel.General
                is MotivationalNote.Source.ByUser -> MotivationalNotesViewState.MotivationalNoteSourceUiModel.ByUser
            },
        )
    }
}