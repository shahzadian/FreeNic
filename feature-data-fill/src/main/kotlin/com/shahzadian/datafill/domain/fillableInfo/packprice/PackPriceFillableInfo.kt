package com.shahzadian.datafill.domain.fillableInfo.packprice

import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent

/**
 * Fillable info for entering cigarette pack price. For now it contains no fields,
 * the page is empty as per requirement.
 */
internal data class PackPriceFillableInfo(
    val price: Int? = null,
    val currency: String,
    val currencies: List<String>,
) : FillableInfo

internal sealed interface PackPriceFillableInfoEvent : FillableInfoEvent {
    data class OnPriceChanged(val price: String, val currency: String) : PackPriceFillableInfoEvent
}
