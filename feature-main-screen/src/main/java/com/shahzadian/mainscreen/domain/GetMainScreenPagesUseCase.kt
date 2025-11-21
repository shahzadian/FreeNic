package com.shahzadian.mainscreen.domain

import com.shahzadian.mainscreen.data.MainScreenPageRepository
import com.shahzadian.mainscreen.model.MainScreenPage
import javax.inject.Inject

internal interface GetMainScreenPagesUseCase {

    suspend operator fun invoke(): List<MainScreenPage>
}

internal class GetMainScreenPagesUseCaseImpl @Inject constructor(
    private val mainScreenPageRepository: MainScreenPageRepository
) : GetMainScreenPagesUseCase {

    override suspend fun invoke(): List<MainScreenPage> {
        return mainScreenPageRepository.getMainScreenPages()
    }
}
