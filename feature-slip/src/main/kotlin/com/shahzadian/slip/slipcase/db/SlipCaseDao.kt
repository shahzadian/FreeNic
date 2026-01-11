package com.shahzadian.slip.slipcase.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface SlipCaseDao {

    @Query("SELECT * FROM slipcaseentity")
    fun getAll(): List<SlipCaseEntity>

    @Upsert
    fun insert(slipCaseEntity: SlipCaseEntity)
}
