package com.shahzadian.datafill.domain.fillableInfo

import com.shahzadian.datafill.domain.fillableInfo.aboutinfo.AboutScreenFillableInfoProvider
import com.shahzadian.datafill.domain.fillableInfo.initinfo.InitScreenFillableInfoProvider
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal interface FillableInfoProviderFactory {

    fun getProviders(
        coroutineScope: CoroutineScope,
    ): List<FillableInfoProvider<*, *>>
}

internal class FillableInfoProviderFactoryImpl @Inject constructor() : FillableInfoProviderFactory {

    override fun getProviders(coroutineScope: CoroutineScope): List<FillableInfoProvider<*, *>> {
        return listOf(
            InitScreenFillableInfoProvider(coroutineScope),
            AboutScreenFillableInfoProvider(coroutineScope),
        )
    }
}
