package com.shahzadian.cravings.cravingcase.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface CravingCaseDao {

    @Insert
    suspend fun insert(cravingCase: CravingCaseEntity)

    @Query("SELECT * FROM cravingcaseentity")
    suspend fun getAll(): List<CravingCaseEntity>

    @Query("SELECT * FROM cravingcaseentity ORDER BY millis DESC LIMIT 1")
    suspend fun getLatestCravingCase(): CravingCaseEntity?

    @Query("DELETE FROM cravingcaseentity WHERE :millis < millis")
    suspend fun deleteByMillis(millis: Long)
}
