package com.shahzadian.slip.slipcase.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal class SlipCaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val slipReasonId: String,
    val slipDateMillis: Long,
)