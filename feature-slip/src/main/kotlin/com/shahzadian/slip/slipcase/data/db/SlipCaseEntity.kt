package com.shahzadian.slip.slipcase.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal class SlipCaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val slipId: String,
    val slipDateMillis: Long,
)