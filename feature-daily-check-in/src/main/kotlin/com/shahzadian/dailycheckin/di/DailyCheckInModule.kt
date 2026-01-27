package com.shahzadian.dailycheckin.di

import android.content.Context
import androidx.room.Room
import com.shahzadian.dailycheckin.data.DailyCheckInRepository
import com.shahzadian.dailycheckin.data.DailyCheckInRepositoryImpl
import com.shahzadian.dailycheckin.db.DailyCheckInDatabase
import com.shahzadian.dailycheckin.domain.AddDailyCheckInStateUseCase
import com.shahzadian.dailycheckin.domain.AddDailyCheckInStateUseCaseImpl
import com.shahzadian.dailycheckin.domain.ClearAllDailyCheckInsUseCase
import com.shahzadian.dailycheckin.domain.ClearAllDailyCheckInsUseCaseImpl
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCase
import com.shahzadian.dailycheckin.domain.GetDailyCheckInForDayUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DailyCheckInModule {

    @Binds
    fun bindDailyCheckInRepository(impl: DailyCheckInRepositoryImpl): DailyCheckInRepository

    @Binds
    fun bindGetDailyCheckInForDayUseCase(impl: GetDailyCheckInForDayUseCaseImpl): GetDailyCheckInForDayUseCase

    @Binds
    fun bindAddDailyCheckInStateUseCase(impl: AddDailyCheckInStateUseCaseImpl): AddDailyCheckInStateUseCase

    @Binds
    fun clearAllDailyCheckInsUseCase(impl: ClearAllDailyCheckInsUseCaseImpl): ClearAllDailyCheckInsUseCase

    companion object {

        @Provides
        @Singleton
        fun provideSlipCaseDatabase(
            @ApplicationContext
            context: Context
        ): DailyCheckInDatabase {
            return Room
                .databaseBuilder(
                    context,
                    DailyCheckInDatabase::class.java,
                    "daily_check_in_database"
                )
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}
