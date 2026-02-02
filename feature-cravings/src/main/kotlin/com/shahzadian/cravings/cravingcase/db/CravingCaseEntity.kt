package com.shahzadian.cravings.cravingcase.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal class CravingCaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val strength: String,
    val millis: Long,
)
