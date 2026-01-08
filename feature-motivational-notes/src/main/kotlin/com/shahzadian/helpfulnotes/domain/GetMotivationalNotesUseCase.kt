package com.shahzadian.helpfulnotes.domain

import com.shahzadian.helpfulnotes.data.MotivationalNotesRepository
import javax.inject.Inject

interface GetMotivationalNotesUseCase {

    suspend operator fun invoke(): List<MotivationalNote>
}

internal class GetMotivationalNotesUseCaseImpl @Inject constructor(
    private val motivationalNotesRepository: MotivationalNotesRepository
) : GetMotivationalNotesUseCase {

    override suspend fun invoke(): List<MotivationalNote> {
        return motivationalNotesRepository.getAllNotes()
    }
}
