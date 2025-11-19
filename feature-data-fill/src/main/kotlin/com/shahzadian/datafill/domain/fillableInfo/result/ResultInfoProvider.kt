package com.shahzadian.datafill.domain.fillableInfo.result

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableDataSharedStateHolder
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableInfoSharedState
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import com.shahzadian.user.model.UserCigarettesData
import com.shahzadian.user.model.UserData
import com.shahzadian.user.model.UserPersonalData
import kotlinx.coroutines.CoroutineScope

internal class ResultInfoProvider(
    coroutineScope: CoroutineScope,
    private val sharedStateHolder: FillableDataSharedStateHolder,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) : FillableInfoProvider<ResultFillableInfo, ResultFillableInfoEvent>(
    coroutineScope
) {

    override val initState: ResultFillableInfo by lazyUnsafe {
        createFillableInfo()
    }

    override fun validateCurrentInfo(): ResultFillableInfo {
        return createFillableInfo()
    }

    override fun updateSharedState(
        currentInfo: ResultFillableInfo,
        currentSharedState: FillableInfoSharedState
    ): FillableInfoSharedState {
        return currentSharedState
    }

    override suspend fun onSubmit() {
        super.onSubmit()
        val userData = sharedStateHolder.sharedState.toUserData() ?: return
        //   updateUserDataUseCase(userData)
    }

    override suspend fun handleFillableInfoEvent(event: ResultFillableInfoEvent): ResultFillableInfo? {
        return currentInfo
    }

    private fun FillableInfoSharedState.toUserData(): UserData? {
        val name = this.name ?: return null
        val age = this.age ?: return null
        val cigarettesPerDay = this.cigarettesPerDay ?: return null
        val packsPerDay = this.packsPerDay ?: return null
        val packPrice = this.packPrice ?: return null
        val currency = this.currency ?: return null
        return UserData(
            personalData = UserPersonalData(
                name = name,
                age = age,
            ),
            cigarettesData = UserCigarettesData(
                cigarettesPerDay = cigarettesPerDay,
                packsPerDay = packsPerDay,
                currency = currency,
                price = packPrice
            )
        )
    }

    private fun createFillableInfo(): ResultFillableInfo {
        val name = requireNotNull(sharedStateHolder.sharedState.name)
        val age = requireNotNull(sharedStateHolder.sharedState.age)
        val cigarettesPerDay = requireNotNull(sharedStateHolder.sharedState.cigarettesPerDay)
        return ResultFillableInfo(
            userInfo = ResultFillableInfo.UserInfo(
                name = name,
                age = age,
                cigarettesPerDay = cigarettesPerDay
            )
        )
    }
}
