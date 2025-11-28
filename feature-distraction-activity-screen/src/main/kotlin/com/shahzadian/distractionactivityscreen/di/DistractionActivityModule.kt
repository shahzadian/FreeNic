package com.shahzadian.distractionactivityscreen.di

import com.shahzadian.distractionactivityscreen.domain.GetDistractionActivitiesUseCase
import com.shahzadian.distractionactivityscreen.domain.GetDistractionActivitiesUseCaseImpl
import com.shahzadian.distractionactivityscreen.guessshape.data.GuessShapesRepository
import com.shahzadian.distractionactivityscreen.guessshape.data.GuessShapesRepositoryImpl
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelper
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelperFactory
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelperFactoryImpl
import com.shahzadian.distractionactivityscreen.guessshape.domain.GuessShapeProgressHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DistractionActivityModule {

    @Binds
    fun bindGetDistractionActivityUseCase(impl: GetDistractionActivitiesUseCaseImpl): GetDistractionActivitiesUseCase

    @Binds
    fun bindGuessShapesRepository(impl: GuessShapesRepositoryImpl): GuessShapesRepository

    @Binds
    fun bindGuessShapeProgressHelper(impl: GuessShapeProgressHelperImpl): GuessShapeProgressHelper

    @Binds
    fun bindGuessShapeProgressHelperFactory(impl: GuessShapeProgressHelperFactoryImpl): GuessShapeProgressHelperFactory
}
