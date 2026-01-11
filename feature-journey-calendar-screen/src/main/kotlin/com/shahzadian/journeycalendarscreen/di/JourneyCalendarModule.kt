package com.shahzadian.journeycalendarscreen.di

import com.shahzadian.journeycalendarscreen.ui.calendarhelper.JourneyCalendarHelper
import com.shahzadian.journeycalendarscreen.ui.calendarhelper.JourneyCalendarHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface JourneyCalendarModule {

    @Binds
    fun bindCalendarHelper(
        impl: JourneyCalendarHelperImpl
    ): JourneyCalendarHelper
}
