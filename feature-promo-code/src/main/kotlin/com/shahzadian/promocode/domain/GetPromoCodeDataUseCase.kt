package com.shahzadian.promocode.domain

import com.shahzadian.promocode.data.PromoCodesRepository
import javax.inject.Inject

interface GetPromoCodeDataUseCase {

    suspend operator fun invoke(promoCode: String): PromoCodeData?
}

internal class GetPromoCodeDataUseCaseImpl @Inject constructor(private val repository: PromoCodesRepository) : GetPromoCodeDataUseCase {

    override suspend fun invoke(promoCode: String): PromoCodeData? {
        return repository.getData(promoCode)
    }
}