package com.shahzadian.cravings.di

import android.content.Context
import androidx.room.Room
import com.shahzadian.cravings.cravingcase.data.CravingCaseRepository
import com.shahzadian.cravings.cravingcase.data.CravingCaseRepositoryImpl
import com.shahzadian.cravings.cravingcase.db.CravingCaseDatabase
import com.shahzadian.cravings.cravingcase.domain.AddCravingCaseUseCase
import com.shahzadian.cravings.cravingcase.domain.AddCravingCaseUseCaseImpl
import com.shahzadian.cravings.cravingcase.domain.ClearCravingCasesUseCase
import com.shahzadian.cravings.cravingcase.domain.ClearCravingCasesUseCaseImpl
import com.shahzadian.cravings.cravingcase.domain.DeleteCravingCasesEarlierThanMillisUseCase
import com.shahzadian.cravings.cravingcase.domain.DeleteCravingCasesEarlierThanMillisUseCaseImpl
import com.shahzadian.cravings.cravingcase.domain.GetCravingCasesUseCase
import com.shahzadian.cravings.cravingcase.domain.GetCravingCasesUseCaseImpl
import com.shahzadian.cravings.data.CravingToolsRepository
import com.shahzadian.cravings.data.CravingToolsRepositoryImpl
import com.shahzadian.cravings.domain.GetCravingToolsUseCase
import com.shahzadian.cravings.domain.GetCravingToolsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CravingsModule {

    @Binds
    fun bindGetCravingsUseCase(impl: GetCravingToolsUseCaseImpl): GetCravingToolsUseCase

    @Binds
    fun bindCravingsRepository(impl: CravingToolsRepositoryImpl): CravingToolsRepository

    @Binds
    fun bindCravingCaseRepository(impl: CravingCaseRepositoryImpl): CravingCaseRepository

    @Binds
    fun bindGetCravingCasesUseCase(impl: GetCravingCasesUseCaseImpl): GetCravingCasesUseCase

    @Binds
    fun addCravingCaseUseCase(impl: AddCravingCaseUseCaseImpl): AddCravingCaseUseCase

    @Binds
    fun clearCravingCasesUseCase(impl: ClearCravingCasesUseCaseImpl): ClearCravingCasesUseCase

    @Binds
    fun bindDeleteCravingCasesEarlierThanMillisUseCase(
        impl: DeleteCravingCasesEarlierThanMillisUseCaseImpl
    ) : DeleteCravingCasesEarlierThanMillisUseCase

    companion object {

        @Provides
        @Singleton
        fun provideCravingCaseDatabase(
            @ApplicationContext
            context: Context,
        ): CravingCaseDatabase {
            return Room
                .databaseBuilder(
                    context,
                    CravingCaseDatabase::class.java,
                    "craving_case_database"
                )
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}
