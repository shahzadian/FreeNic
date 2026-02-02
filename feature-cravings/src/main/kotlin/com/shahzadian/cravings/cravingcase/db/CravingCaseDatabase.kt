package com.shahzadian.cravings.cravingcase.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CravingCaseEntity::class], version = 1, exportSchema = false)
internal abstract class CravingCaseDatabase : RoomDatabase() {

    abstract fun cravingCaseDao(): CravingCaseDao
}
