package com.shahzadian.helpfulnotes.domain

import com.shahzadian.helpfulnotes.data.HelpfulNotesRepository
import javax.inject.Inject

interface GetHelpfulNotesUseCase {

    suspend operator fun invoke(): List<HelpfulNote>
}

internal class GetHelpfulNotesUseCaseImpl @Inject constructor(
    private val helpfulNotesRepository: HelpfulNotesRepository
) : GetHelpfulNotesUseCase {

    override suspend fun invoke(): List<HelpfulNote> {
        return helpfulNotesRepository.getAllNotes()
    }
}
