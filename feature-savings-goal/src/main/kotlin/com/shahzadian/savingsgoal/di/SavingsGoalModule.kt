package com.shahzadian.savingsgoal.di

import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.SavingsGoalRepositoryImpl
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepositoryImpl
import com.shahzadian.savingsgoal.data.datastore.SavingsGoalDataStoreProvider
import com.shahzadian.savingsgoal.data.datastore.SavingsGoalDataStoreProviderImpl
import com.shahzadian.savingsgoal.domain.SavingsGoalHelper
import com.shahzadian.savingsgoal.domain.SavingsGoalHelperImpl
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapper
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SavingsGoalModule {

    @Binds
    @Singleton
    fun bindSavingsGoalDataStoreProvider(
        impl: SavingsGoalDataStoreProviderImpl
    ): SavingsGoalDataStoreProvider

    @Binds
    fun bindSavingsGoalRepository(impl: SavingsGoalRepositoryImpl): SavingsGoalRepository

    @Binds
    fun bindGetSavingsGoalUseCase(impl: SavingsGoalHelperImpl): SavingsGoalHelper

    @Binds
    fun bindSavingsGoalCategoryRepository(impl: SavingsGoalCategoryRepositoryImpl): SavingsGoalCategoryRepository

    @Binds
    fun bindSavingsGoalMapper(impl: SavingsGoalMapperImpl): SavingsGoalMapper
}
