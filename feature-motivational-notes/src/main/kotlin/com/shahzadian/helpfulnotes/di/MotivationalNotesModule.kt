package com.shahzadian.helpfulnotes.di

import com.shahzadian.helpfulnotes.data.MotivationalNotesRepository
import com.shahzadian.helpfulnotes.data.MotivationalNotesRepositoryImpl
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCase
import com.shahzadian.helpfulnotes.domain.GetMotivationalNotesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MotivationalNotesModule {

    @Binds
    fun bindHelpfulNotesRepository(
        impl: MotivationalNotesRepositoryImpl
    ): MotivationalNotesRepository

    @Binds
    fun bindGetHelpfulNotesUseCase(
        impl: GetMotivationalNotesUseCaseImpl
    ): GetMotivationalNotesUseCase
}
