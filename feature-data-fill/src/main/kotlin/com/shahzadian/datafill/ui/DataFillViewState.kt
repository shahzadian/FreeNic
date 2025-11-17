package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import java.util.Currency

@Immutable
internal sealed interface DataFillViewState : ViewState {

    val pagesData: PagesData?

    data object Loading : DataFillViewState {
        override val pagesData: PagesData? = null
    }

    data class Init(
        override val pagesData: PagesData
    ) : DataFillViewState

    @Immutable
    data class About(
        override val pagesData: PagesData,
        val name: String? = null,
        val age: String? = null,
    ) : DataFillViewState

    data class CigarettesPerDay(
        override val pagesData: PagesData,
        val cigarettesCountIntRange: IntRange,
        val cigarettesPerDay: String,
        val packsPerDay: String,
        val priceData: PriceData? = null,
    ) : DataFillViewState {

        data class PriceData(
            val price: String,
            val currency: Currency
        )
    }

    data class PagesData(
        val pagesCount: Int,
        val currentPageIndex: Int,
    )
}
