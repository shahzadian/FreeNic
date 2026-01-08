package com.shahzadian.motivationalnotesscreen.ui.viewmodel

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R

@Immutable
internal sealed interface MotivationalNotesViewState : ViewState {

    @Immutable
    data object Loading : MotivationalNotesViewState

    data class Data(
        val notes: List<MotivationalNoteUiModel>,
    ) : MotivationalNotesViewState

    @Immutable
    data class MotivationalNoteUiModel(
        val text: TextWrapper,
        val source: MotivationalNoteSourceUiModel
    )

    @Immutable
    sealed class MotivationalNoteSourceUiModel(
        private val badgeTextRes: Int,
    ) {

        fun getBadgeText(): TextWrapper {
            return TextWrapper.FromRes(badgeTextRes)
        }

        data object General : MotivationalNoteSourceUiModel(
            badgeTextRes = R.string.motivational_notes_badge_general
        )
        data object ByUser : MotivationalNoteSourceUiModel(
            badgeTextRes = R.string.motivational_notes_badge_by_user
        )
    }
}
