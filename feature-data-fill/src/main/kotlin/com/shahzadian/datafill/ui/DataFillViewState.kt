package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState

@Immutable
internal sealed interface DataFillViewState : ViewState {

    val pagesData: PagesData?

    val submitButtonEnabled: Boolean

    data object Loading : DataFillViewState {
        override val pagesData: PagesData? = null
        override val submitButtonEnabled: Boolean = true
    }

    data class Init(
        override val pagesData: PagesData,
    ) : DataFillViewState {
        override val submitButtonEnabled: Boolean = true
    }

    @Immutable
    data class About(
        override val pagesData: PagesData,
        override val submitButtonEnabled: Boolean,
        val name: String? = null,
        val age: String? = null,
    ) : DataFillViewState

    data class CigarettesPerDay(
        override val pagesData: PagesData,
        override val submitButtonEnabled: Boolean,
        val cigarettesCountIntRange: IntRange,
        val cigarettesPerDay: String,
        val packsPerDay: String,
    ) : DataFillViewState

    data class PackPrice(
        override val pagesData: PagesData,
        override val submitButtonEnabled: Boolean,
        val price: PriceData? = null,
    ) : DataFillViewState {

        data class PriceData(
            val price: String,
            val currency: String,
            val currencies: List<String>,
        )
    }

    data class PagesData(
        val pagesCount: Int,
        val currentPageIndex: Int,
    )
}
