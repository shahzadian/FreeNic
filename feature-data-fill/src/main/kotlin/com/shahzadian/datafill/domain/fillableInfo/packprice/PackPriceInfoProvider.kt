package com.shahzadian.datafill.domain.fillableInfo.packprice

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import kotlinx.coroutines.CoroutineScope
import java.util.Currency
import java.util.Locale

internal class PackPriceInfoProvider(
    coroutineScope: CoroutineScope,
) : FillableInfoProvider<PackPriceFillableInfo, PackPriceFillableInfoEvent>(
    coroutineScope
) {

    private val currencies: List<String> by lazyUnsafe {
        listOf(
            Currency.getInstance(Locale.US).currencyCode,
            Currency.getInstance(Locale.UK).currencyCode,
            Currency.getInstance(Locale.FRANCE).currencyCode,
            "RUB"
        ).sorted().plus("Other")
    }

    override val initState: PackPriceFillableInfo by lazyUnsafe {
        PackPriceFillableInfo(
            price = null,
            currency = Currency.getInstance(Locale.US).currencyCode,
            currencies = currencies,
        )
    }

    override fun onFillInfoSubmit() {
        // No-op for now
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
