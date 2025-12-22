package com.shahzadian.savinggoal.di

import com.shahzadian.savinggoal.data.SavingGoalRepository
import com.shahzadian.savinggoal.data.SavingGoalRepositoryImpl
import com.shahzadian.savinggoal.data.datastore.SavingGoalDataStoreProvider
import com.shahzadian.savinggoal.data.datastore.SavingGoalDataStoreProviderImpl
import com.shahzadian.savinggoal.domain.SavingGoalHelper
import com.shahzadian.savinggoal.domain.SavingGoalHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SavingGoalModule {

    @Binds
    @Singleton
    fun bindSavingGoalDataStoreProvider(
        impl: SavingGoalDataStoreProviderImpl
    ): SavingGoalDataStoreProvider

    @Binds
    fun bindSavingGoalRepository(impl: SavingGoalRepositoryImpl): SavingGoalRepository

    @Binds
    fun bindGetSavingGoalUseCase(impl: SavingGoalHelperImpl): SavingGoalHelper
}
