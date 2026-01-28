package com.shahzadian.slip.slipcase.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface SlipCaseDao {

    @Query("SELECT * FROM slipcaseentity")
    fun getAll(): List<SlipCaseEntity>

    @Query("DELETE FROM slipcaseentity WHERE slipDateMillis = :millis")
    fun deleteByMillis(millis: Long)

    @Upsert
    fun insert(slipCaseEntity: SlipCaseEntity)
}
