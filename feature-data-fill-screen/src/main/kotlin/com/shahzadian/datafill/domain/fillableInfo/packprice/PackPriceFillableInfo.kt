package com.shahzadian.datafill.domain.fillableInfo.packprice

import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.datafill.domain.fillableInfo.FillableInfo
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoEvent

/**
 * Fillable info for entering cigarette pack price. For now it contains no fields,
 * the page is empty as per requirement.
 */
internal data class PackPriceFillableInfo(
    val price: Float? = null,
    val currency: String,
    val quitDateMillis: Long,
    val displayableQuitDate: TextWrapper,
) : FillableInfo {

    override fun isValid(): Boolean {
        return price != null && price > 0
    }
}

internal sealed interface PackPriceFillableInfoEvent : FillableInfoEvent {
    data class OnPriceChanged(val price: String) : PackPriceFillableInfoEvent
    data class OnQuitDateChanged(val quitDateMillis: Long) : PackPriceFillableInfoEvent
}
