package com.shahzadian.journeycalendarscreen.di

import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarHelper
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarHelperImpl
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarRewardHelper
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarRewardHelperImpl
import com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder.JourneyCalendarEventsFinderFactory
import com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder.JourneyCalendarEventsFinderFactoryImpl
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

    @Binds
    fun bindCalendarEventsFinderFactory(
        impl: JourneyCalendarEventsFinderFactoryImpl
    ): JourneyCalendarEventsFinderFactory

    @Binds
    fun bindJourneyCalendarRewardHelper(
        impl: JourneyCalendarRewardHelperImpl
    ): JourneyCalendarRewardHelper
}