package com.shahzadian.datafill.domain.fillableInfo.packprice

import android.text.format.DateUtils
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import kotlinx.coroutines.CoroutineScope
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
            quitDateMillis = System.currentTimeMillis(),
            displayableQuitDate = getDisplayableDateByMillis(System.currentTimeMillis()),
        )
    }

    override fun updateSharedState(
        currentInfo: PackPriceFillableInfo,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState {
        return currentSharedState.copy(
            packPrice = currentInfo.price,
            currency = currentInfo.currency,
            quitDate = currentInfo.quitDateMillis,
            displayableQuitDate = currentInfo.displayableQuitDate
        )
    }

    override suspend fun handleFillableInfoEvent(event: PackPriceFillableInfoEvent): PackPriceFillableInfo? {
        return when (event) {
            is PackPriceFillableInfoEvent.OnPriceChanged -> {
                currentInfo.copy(
                    price = event.price.toFloatOrNull(),
                    currency = Currency.getInstance(Locale.US).currencyCode
                )
            }

            is PackPriceFillableInfoEvent.OnQuitDateChanged -> {
                currentInfo.copy(
                    quitDateMillis = event.quitDateMillis,
                    displayableQuitDate = getDisplayableDateByMillis(event.quitDateMillis)
                )
            }
        }
    }

    private fun getDisplayableDateByMillis(millis: Long): TextWrapper {
        val isToday = DateUtils.isToday(millis)
        return if (isToday) {
            TextWrapper.FromRes(R.string.data_fill_quit_date_today)
        } else {
            TextWrapper.FromString(extractDisplayableQuitDate(millis))
        }
    }

    private fun extractDisplayableQuitDate(quitDateMillis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return Instant.ofEpochMilli(quitDateMillis)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }
}
