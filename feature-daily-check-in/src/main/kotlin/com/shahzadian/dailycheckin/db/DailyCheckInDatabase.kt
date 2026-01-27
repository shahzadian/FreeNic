package com.shahzadian.dailycheckin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DailyCheckInEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
internal abstract class DailyCheckInDatabase : RoomDatabase() {

    abstract fun dailyCheckInDao(): DailyCheckInDao
}
