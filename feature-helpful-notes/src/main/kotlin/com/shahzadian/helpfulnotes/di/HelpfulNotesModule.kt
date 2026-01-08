package com.shahzadian.helpfulnotes.di

import com.shahzadian.helpfulnotes.data.HelpfulNotesRepository
import com.shahzadian.helpfulnotes.data.HelpfulNotesRepositoryImpl
import com.shahzadian.helpfulnotes.domain.GetHelpfulNotesUseCase
import com.shahzadian.helpfulnotes.domain.GetHelpfulNotesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface HelpfulNotesModule {

    @Binds
    fun bindHelpfulNotesRepository(
        impl: HelpfulNotesRepositoryImpl
    ): HelpfulNotesRepository

    @Binds
    fun bindGetHelpfulNotesUseCase(
        impl: GetHelpfulNotesUseCaseImpl
    ): GetHelpfulNotesUseCase
}
