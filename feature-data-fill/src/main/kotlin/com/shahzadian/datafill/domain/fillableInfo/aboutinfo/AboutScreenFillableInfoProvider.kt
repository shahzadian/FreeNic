package com.shahzadian.datafill.domain.fillableInfo.aboutinfo

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.datafill.domain.fillableInfo.FillableInfoProvider
import kotlinx.coroutines.CoroutineScope

internal class AboutScreenFillableInfoProvider(
    coroutineScope: CoroutineScope
) : FillableInfoProvider<AboutScreenFillableInfo, AboutScreenFillableInfoEvent>(
    coroutineScope = coroutineScope,
) {

    override val initState: AboutScreenFillableInfo by lazyUnsafe {
        AboutScreenFillableInfo(
            name = null,
            age = null,
        )
    }

    override fun onFillInfoSubmit() {
//        TODO save data in repository
    }

    override suspend fun handleFillableInfoEvent(event: AboutScreenFillableInfoEvent): AboutScreenFillableInfo? {
        return when (event) {
            is AboutScreenFillableInfoEvent.OnNameChanged -> {
                currentInfo.copy(name = event.name)
            }

            is AboutScreenFillableInfoEvent.OnAgeChanged -> {
                currentInfo.copy(age = event.age)
            }
        }
    }
}
