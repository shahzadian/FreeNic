package com.shahzadian.dailycheckin.domain

import java.time.LocalDate

class DailyCheckInState(
    val mood: DailyCheckInMood,
    val date: LocalDate,
)
