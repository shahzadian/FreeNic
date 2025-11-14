package com.shahzadian.datafill.ui

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import javax.inject.Inject

internal class DataFillViewModel @Inject constructor() :
    BaseViewModel<DataFillViewState, DataFillViewEvent, DataFillCommand>() {

    override val initState: DataFillViewState by lazyUnsafe {
        DataFillViewState.Loading
    }

    override fun onViewEventHappened(event: DataFillViewEvent) {

    }
}