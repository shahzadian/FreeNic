package com.shahzadian.slip.slipcase.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface SlipCaseDao {

    @Query("SELECT * FROM slip_entity")
    fun getAll(): List<SlipCaseEntity>

    @Upsert
    fun insert(slipCaseEntity: SlipCaseEntity)
}
