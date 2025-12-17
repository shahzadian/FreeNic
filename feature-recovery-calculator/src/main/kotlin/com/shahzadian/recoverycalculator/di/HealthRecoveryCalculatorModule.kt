package com.shahzadian.recoverycalculator.di

import com.shahzadian.recoverycalculator.domain.factory.HealthRecoveryCalculatorFactory
import com.shahzadian.recoverycalculator.domain.factory.HealthRecoveryCalculatorFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface HealthRecoveryCalculatorModule {

    @Binds
    fun bindHealthRecoveryCalculatorFactory(
        impl: HealthRecoveryCalculatorFactoryImpl
    ): HealthRecoveryCalculatorFactory
}
