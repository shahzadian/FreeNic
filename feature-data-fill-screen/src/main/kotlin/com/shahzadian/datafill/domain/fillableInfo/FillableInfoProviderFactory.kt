package com.shahzadian.datafill.domain.fillableInfo

import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.cigarettesperday.CigarettesPerDayInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.initinfo.InitScreenFillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.packprice.PackPriceInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.result.ResultInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.sharedstate.FillableDataSharedStateHolder
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal interface FillableInfoProviderFactory {

    fun getProviders(
        coroutineScope: CoroutineScope,
        sharedStateHolder: FillableDataSharedStateHolder,
    ): List<FillableInfoProvider<*, *>>
}

internal class FillableInfoProviderFactoryImpl @Inject constructor(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) : FillableInfoProviderFactory {

    override fun getProviders(
        coroutineScope: CoroutineScope,
        sharedStateHolder: FillableDataSharedStateHolder,
    ): List<FillableInfoProvider<*, *>> {
        return listOf(
            InitScreenFillableInfoProvider(coroutineScope),
            AboutScreenFillableInfoProvider(coroutineScope),
            CigarettesPerDayInfoProvider(coroutineScope),
            PackPriceInfoProvider(coroutineScope),
            ResultInfoProvider(
                coroutineScope = coroutineScope,
                sharedStateHolder = sharedStateHolder,
            )
        )
    }
}
