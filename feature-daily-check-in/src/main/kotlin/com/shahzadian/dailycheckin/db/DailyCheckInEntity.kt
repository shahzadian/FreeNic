package com.shahzadian.dailycheckin.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
internal class DailyCheckInEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mood: String,
    val date: LocalDate,
)
