package com.shahzadian.helpfulnotes.data

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.helpfulnotes.domain.MotivationalNote
import javax.inject.Inject

interface MotivationalNotesRepository {

    suspend fun getAllNotes(): List<MotivationalNote>
}

internal class MotivationalNotesRepositoryImpl @Inject constructor() : MotivationalNotesRepository {

    override suspend fun getAllNotes(): List<MotivationalNote> {
        return listOf(
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_1),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_2),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_3),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_4),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_5),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_6),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_7),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_8),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_9),
                source = MotivationalNote.Source.General,
            ),
            MotivationalNote(
                text = TextWrapper.FromRes(R.string.motivational_note_10),
                source = MotivationalNote.Source.General,
            ),
        )
    }
}
