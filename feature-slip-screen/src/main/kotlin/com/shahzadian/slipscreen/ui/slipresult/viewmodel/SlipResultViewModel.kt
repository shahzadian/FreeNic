package com.shahzadian.slipscreen.ui.slipresult.viewmodel

import androidx.lifecycle.viewModelScope
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.vm.BaseViewModel
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SlipResultViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val userDataHelperFactory: UserDataHelperFactory,
) : BaseViewModel<SlipResultViewState, SlipResultViewEvent, SlipResultViewCommand>() {

    private var userData: UserData? = null
    private var userDataHelper: UserDataHelper? = null

    override val initState: SlipResultViewState by lazyUnsafe {
        SlipResultViewState.Loading
    }

    init {
        setData()
    }

    override fun onViewEventHappened(event: SlipResultViewEvent) {

    }

    private fun setData() {
        viewModelScope
            .launch {
                val noCigDays = getUserDataHelper().getSkippedDaysCount()
                setViewState(
                    SlipResultViewState.Data(
                        noCigDays = noCigDays
                    )
                )
            }
    }

    private suspend fun getUserData(): UserData {
        return userData ?: let {
            val data = getUserDataUseCase()
                ?: throw IllegalStateException("User didn't found")
            userData = data
            data
        }
    }

    private suspend fun getUserDataHelper(): UserDataHelper {
        return userDataHelper ?: let {
            val data = userDataHelperFactory.create(getUserData())
            userDataHelper = data
            data
        }
    }
}
