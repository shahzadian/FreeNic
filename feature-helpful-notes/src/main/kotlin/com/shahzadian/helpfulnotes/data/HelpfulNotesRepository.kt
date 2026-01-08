package com.shahzadian.helpfulnotes.data

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.helpfulnotes.domain.HelpfulNote
import javax.inject.Inject

interface HelpfulNotesRepository {

    suspend fun getAllNotes(): List<HelpfulNote>
}

internal class HelpfulNotesRepositoryImpl @Inject constructor() : HelpfulNotesRepository {

    override suspend fun getAllNotes(): List<HelpfulNote> {
        return listOf(
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_1),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_2),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_3),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_4),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_5),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_6),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_7),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_8),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_9),
                source = HelpfulNote.Source.General,
            ),
            HelpfulNote(
                text = TextWrapper.FromRes(R.string.helpful_note_10),
                source = HelpfulNote.Source.General,
            ),
        )
    }
}
