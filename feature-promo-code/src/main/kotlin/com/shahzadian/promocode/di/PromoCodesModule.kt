package com.shahzadian.promocode.di

import com.shahzadian.promocode.data.PromoCodesRepository
import com.shahzadian.promocode.data.PromoCodesRepositoryImpl
import com.shahzadian.promocode.domain.GetPromoCodeDataUseCase
import com.shahzadian.promocode.domain.GetPromoCodeDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface PromoCodesModule {

    @Binds
    @Singleton
    fun bindPromoCodesRepository(
        impl: PromoCodesRepositoryImpl
    ) : PromoCodesRepository

    @Binds
    fun bindGetPromoCodeDataUseCase(impl: GetPromoCodeDataUseCaseImpl): GetPromoCodeDataUseCase
}
