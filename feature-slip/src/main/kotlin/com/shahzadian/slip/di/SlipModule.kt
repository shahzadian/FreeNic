package com.shahzadian.slip.di

import android.content.Context
import androidx.room.Room
import com.shahzadian.slip.slipcase.data.SlipCaseRepository
import com.shahzadian.slip.slipcase.data.SlipCaseRepositoryImpl
import com.shahzadian.slip.slipcase.db.SlipCaseDatabase
import com.shahzadian.slip.slipcase.domain.CreateSlipCaseUseCase
import com.shahzadian.slip.slipcase.domain.CreateSlipCaseUseCaseImpl
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCase
import com.shahzadian.slip.slipcase.domain.GetSlipCasesUseCaseImpl
import com.shahzadian.slip.slipreason.data.SlipReasonRepository
import com.shahzadian.slip.slipreason.data.SlipReasonRepositoryImpl
import com.shahzadian.slip.slipreason.domain.GetSlipReasonsUseCase
import com.shahzadian.slip.slipreason.domain.GetSlipReasonsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SlipModule {

    @Binds
    fun bindSlipContextRepository(
        impl: SlipReasonRepositoryImpl
    ): SlipReasonRepository

    @Binds
    fun bindGetSlipContextsUseCase(
        impl: GetSlipReasonsUseCaseImpl
    ): GetSlipReasonsUseCase

    @Binds
    fun bindSlipCaseRepository(
        impl: SlipCaseRepositoryImpl
    ): SlipCaseRepository

    @Binds
    fun bindGetSlipCasesUseCase(
        impl: GetSlipCasesUseCaseImpl
    ): GetSlipCasesUseCase

    @Binds
    fun bindCreateSlipCaseUseCase(
        impl: CreateSlipCaseUseCaseImpl
    ): CreateSlipCaseUseCase

    companion object {

        @Provides
        @Singleton
        fun provideSlipCaseDatabase(
            @ApplicationContext
            context: Context
        ): SlipCaseDatabase {
            return Room
                .databaseBuilder(context, SlipCaseDatabase::class.java, "slip_case_database")
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}
