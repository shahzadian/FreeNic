package com.shahzadian.user.di

import com.shahzadian.user.data.UserDataRepository
import com.shahzadian.user.data.UserDataRepositoryImpl
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCaseImpl
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserDataModule {

    @Binds
    fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    fun bindUpdateUserDataUseCase(impl: UpdateUserDataUseCaseImpl): UpdateUserDataUseCase

    @Binds
    fun bindGetUserDataUseCase(impl: GetUserDataUseCaseImpl): GetUserDataUseCase
}
