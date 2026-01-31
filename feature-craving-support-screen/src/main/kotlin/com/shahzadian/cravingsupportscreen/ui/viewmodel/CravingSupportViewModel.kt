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

    private var _motivationalNotes: List<MotivationalNote>? = null

    init {
        setData()
    }

    override fun onViewEventHappened(event: CravingSupportViewEvent) {
        when (event) {
            is CravingSupportViewEvent.OnBackIconClicked -> {
                onBackIconClicked()
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            val motivationalNotes = getMotivationalNotes()
            setViewState(
                CravingSupportViewState.Data(
                    motivationalNote = motivationalNotes.random().text,
                )
            )
        }
    }

    private fun onBackIconClicked() {
        viewModelScope.launch {
            sendViewCommand(CravingSupportViewCommand.NavigateBack)
        }
    }

    private suspend fun getMotivationalNotes(): List<MotivationalNote> {
        return _motivationalNotes ?: let {
            val notes = getMotivationalNotesUseCase()
            _motivationalNotes = notes
            notes
        }
    }
}
