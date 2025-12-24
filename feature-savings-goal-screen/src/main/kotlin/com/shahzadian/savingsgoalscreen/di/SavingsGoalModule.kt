package com.shahzadian.savingsgoalscreen.di

import com.shahzadian.savingsgoalscreen.data.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoalscreen.data.SavingsGoalCategoryRepositoryImpl
import com.shahzadian.savingsgoalscreen.domain.GetSavingsGoalCategoryUseCase
import com.shahzadian.savingsgoalscreen.domain.GetSavingsGoalCategoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SavingsGoalModule {

    @Binds
    fun bindSavingsGoalCategoryRepository(
        impl: SavingsGoalCategoryRepositoryImpl
    ): SavingsGoalCategoryRepository

    @Binds
    fun bindGetSavingsGoalCategoriesUseCase(
        impl: GetSavingsGoalCategoryUseCaseImpl
    ): GetSavingsGoalCategoryUseCase
}
