package com.shahzadian.dailycheckin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDate

@Dao
internal interface DailyCheckInDao {

    @Query("SELECT * FROM dailycheckinentity WHERE date = :date")
    suspend fun getForDay(date: LocalDate): DailyCheckInEntity?

    @Insert
    suspend fun insert(dailyCheckInEntity: DailyCheckInEntity)

    @Query("DELETE FROM dailycheckinentity")
    suspend fun clear()
}
