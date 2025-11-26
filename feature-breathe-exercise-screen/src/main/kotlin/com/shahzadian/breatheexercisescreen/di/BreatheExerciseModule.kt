package com.shahzadian.breatheexercisescreen.di

import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCase
import com.shahzadian.breatheexercisescreen.domain.GetBreatheExerciseSettingsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface BreatheExerciseModule {

    @Binds
    fun bindGetBreatheExerciseUseCase(
        impl: GetBreatheExerciseSettingsUseCaseImpl
    ) : GetBreatheExerciseSettingsUseCase
}
