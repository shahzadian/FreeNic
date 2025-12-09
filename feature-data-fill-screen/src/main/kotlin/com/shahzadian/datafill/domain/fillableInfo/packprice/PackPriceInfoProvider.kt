package com.shahzadian.datafill.domain.fillableInfo.packprice

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import com.shahzadian.user.model.UserCigarettesData
import kotlinx.coroutines.CoroutineScope
import java.util.Currency
import java.util.Locale

internal class PackPriceInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<PackPriceFillableInfo, PackPriceFillableInfoEvent>(
    coroutineScope
) {

    override val initState: PackPriceFillableInfo by lazyUnsafe {
        PackPriceFillableInfo(
            price = null,
            currency = Currency.getInstance(Locale.US).currencyCode,
            currencies = UserCigarettesData.PriceData.getAvailableCurrencies().sorted().plus("Other"),
        )
    }

    override fun updateSharedState(
        currentInfo: PackPriceFillableInfo,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState {
        return currentSharedState.copy(
            packPrice = currentInfo.price,
            currency = currentInfo.currency
        )
    }

    override suspend fun handleFillableInfoEvent(event: PackPriceFillableInfoEvent): PackPriceFillableInfo? {
        return when (event) {
            is PackPriceFillableInfoEvent.OnPriceChanged -> {
                currentInfo.copy(
                    price = event.price.toFloatOrNull(),
                    currency = event.currency
                )
            }
        }
    }
}
