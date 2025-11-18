package com.shahzadian.datafill.domain.fillableInfo.result

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent

internal data class ResultFillableInfo(
    val userInfo: UserInfo,
) : FillableInfo {
    override fun isValid(): Boolean = true

    data class UserInfo(
        val name: String,
        val age: Int,
        val cigarettesPerDay: Int,
    )
}

internal sealed interface ResultFillableInfoEvent : FillableInfoEvent
