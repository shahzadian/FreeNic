package com.shahzadian.journeycalendarscreen.calendarhelper.eventsfinder

import android.content.Context
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.cravings.cravingcase.domain.CravingCase
import com.shahzadian.cravings.cravingcase.domain.CravingStrength
import com.shahzadian.cravings.cravingcase.domain.GetCravingCasesUseCase
import com.shahzadian.journeycalendar.R
import com.shahzadian.journeycalendarscreen.calendarhelper.JourneyCalendarDayEvent
import java.time.LocalDate

internal class CravingCaseEventFinder(
    private val context: Context,
    private val getCravingCasesUseCase: GetCravingCasesUseCase,
) : JourneyCalendarEventsFinder() {

    private var _cravingCases: List<CravingCase>? = null

    override suspend fun getEventsForDate(date: LocalDate): List<JourneyCalendarDayEvent> {
        val cases = getCravingCases()
        return cases.filter {
            it.caseMillis in date.startOfDayMillis()..date.endOfDayMillis()
        }.map { case ->
            JourneyCalendarDayEvent(
                title = TextWrapper.FromRes(
                    com.shahzadian.baseui.R.string.journey_calendar_event_craving_title,
                    listOf(case.strength.getLabel())
                ),
                subtitle = TextWrapper.FromRes(com.shahzadian.baseui.R.string.journey_calendar_event_craving_subtitle),
                icon = ImageWrapper.FromRes(R.drawable.ic_journey_calendar_event_craving)
            )
        }
    }

    private suspend fun getCravingCases(): List<CravingCase> {
        return _cravingCases ?: let {
            val cases = getCravingCasesUseCase()
            _cravingCases = cases
            return@let _cravingCases!!
        }
    }

    private fun CravingStrength.getLabel(): String {
        return when (this) {
            CravingStrength.LOW -> context.getString(com.shahzadian.baseui.R.string.craving_support_craving_strength_low)
            CravingStrength.MEDIUM -> context.getString(com.shahzadian.baseui.R.string.craving_support_craving_strength_mid)
            CravingStrength.STRONG -> context.getString(com.shahzadian.baseui.R.string.craving_support_craving_strength_strong)
        }
    }
}