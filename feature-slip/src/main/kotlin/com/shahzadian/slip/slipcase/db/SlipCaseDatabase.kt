package com.shahzadian.slip.slipcase.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SlipCaseEntity::class], version = 1)
internal abstract class SlipCaseDatabase : RoomDatabase() {

    abstract fun slipCaseDao(): SlipCaseDao
}
