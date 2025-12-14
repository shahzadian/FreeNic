package com.shahzadian.shareimagescreen.di

import com.shahzadian.shareimagescreen.domain.GetUriToShareUseCase
import com.shahzadian.shareimagescreen.domain.GetUriToShareUseCaseImpl
import com.shahzadian.shareimagescreen.domain.ImageShareController
import com.shahzadian.shareimagescreen.domain.ImageShareControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ShareImageModule {

    @Binds
    @Singleton
    fun bindImageShareController(
        impl: ImageShareControllerImpl
    ): ImageShareController

    @Binds
    fun bindShareBitmapUseCase(
        impl: GetUriToShareUseCaseImpl
    ): GetUriToShareUseCase
}
