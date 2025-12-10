package com.shahzadian.mainscreen.ui

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.mainscreen.domain.GetMainScreenPagesUseCase
import com.shahzadian.mainscreen.ui.model.MainScreenItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getMainScreenPages: GetMainScreenPagesUseCase,
) : BaseViewModel<MainViewState, MainViewEvent, MainViewCommand>() {

    override val initState: MainViewState by lazyUnsafe { MainViewState.Loading }

    override fun onViewEventHappened(event: MainViewEvent) {
//
    }

    init {
        viewModelScope.launch {
            val pages = getMainScreenPages()
            val items = pages.map {
                MainScreenItemUiModel(
                    title = it.name,
                    icon = it.icon,
                    route = it.screen.route,
                    isPaid = it.screen.isPaid()
                )
            }
            setViewState(MainViewState.Default(pages = items))
        }
    }
}