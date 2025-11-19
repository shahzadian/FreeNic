package com.shahzadian.datafill.domain.fillableInfo.sharedstate

data class FillableInfoSharedState(
    val name: String? = null,
    val age: Int? = null,
    val cigarettesPerDay: Int? = null,
    val packsPerDay: Float? = null,
    val packPrice: Float? = null,
    val currency: String? = null
)