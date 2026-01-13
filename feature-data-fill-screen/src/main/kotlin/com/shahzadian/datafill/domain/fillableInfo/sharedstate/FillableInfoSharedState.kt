package com.shahzadian.datafill.domain.fillableInfo.sharedstate

import com.freenic.ui.wrapper.TextWrapper

data class FillableInfoSharedState(
    val name: String? = null,
    val age: Int? = null,
    val cigarettesPerDay: Int? = null,
    val packsPerDay: Float? = null,
    val packPrice: Float? = null,
    val currency: String? = null,
    val quitDate: Long? = null,
    val displayableQuitDate: TextWrapper? = null,
)