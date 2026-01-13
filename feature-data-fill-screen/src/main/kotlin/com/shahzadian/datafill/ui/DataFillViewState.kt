package com.shahzadian.datafill.ui

import androidx.compose.runtime.Immutable
import com.freenic.ui.vm.ViewState
import com.freenic.ui.wrapper.TextWrapper

@Immutable
internal sealed interface DataFillViewState : ViewState {

    val pagesData: PagesData?

    val submitButtonEnabled: Boolean

    @Immutable
    data object Loading : DataFillViewState {
        override val pagesData: PagesData? = null
        override val submitButtonEnabled: Boolean = true
    }

    @Immutable
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

    @Immutable
    data class CigarettesPerDay(
        override val pagesData: PagesData,
        override val submitButtonEnabled: Boolean,
        val cigarettesCountIntRange: IntRange,
        val cigarettesPerDay: String,
        val packsPerDay: String,
    ) : DataFillViewState

    @Immutable
    data class PackPrice(
        override val pagesData: PagesData,
        override val submitButtonEnabled: Boolean,
        val quitData: QuitData,
        val price: PriceData? = null,
    ) : DataFillViewState {

        @Immutable
        data class PriceData(
            val price: String,
            val currency: String,
            val currencies: List<String>,
        )

        @Immutable
        data class QuitData(
            val quitDateMillis: Long,
            val displayableQuitDate: TextWrapper,
        )
    }

    @Immutable
    data class Result(
        override val pagesData: PagesData,
        val userData: UserData,
    ) : DataFillViewState {
        override val submitButtonEnabled: Boolean = true

        @Immutable
        data class UserData(
            val name: String,
            val age: String,
            val cigarettesPerDay: String,
        )
    }

    @Immutable
    data class PagesData(
        val pagesCount: Int,
        val currentPageIndex: Int,
    )
}
