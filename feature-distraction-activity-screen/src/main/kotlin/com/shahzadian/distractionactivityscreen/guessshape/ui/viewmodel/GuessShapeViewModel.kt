package com.shahzadian.distractionactivityscreen.guessshape.ui.viewmodel

import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GuessShapeViewModel @Inject constructor(

) : BaseViewModel<GuessShapeViewState, GuessShapeViewEvent, GuessShapeViewCommand>() {

    override val initState: GuessShapeViewState by lazyUnsafe {
        GuessShapeViewState.Loading
    }

    override fun onViewEventHappened(event: GuessShapeViewEvent) {

    }
}
