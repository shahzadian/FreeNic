package com.shahzadian.user.di

import com.shahzadian.user.data.UserDataRepository
import com.shahzadian.user.data.UserDataRepositoryImpl
import com.shahzadian.user.datastore.UserDataStoreProvider
import com.shahzadian.user.datastore.UserDataStoreProviderImpl
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.UserDataHelperFactoryImpl
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCaseImpl
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCase
import com.shahzadian.user.domain.updateuserdata.UpdateUserDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UserDataModule {

    @Binds
    fun bindUserDataRepository(impl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    fun bindUpdateUserDataUseCase(impl: UpdateUserDataUseCaseImpl): UpdateUserDataUseCase

    @Binds
    fun bindGetUserDataUseCase(impl: GetUserDataUseCaseImpl): GetUserDataUseCase

    @Binds
    @Singleton
    fun bindUserDataStoreProvider(impl: UserDataStoreProviderImpl): UserDataStoreProvider

    @Binds
    fun bindUserDataHelperFactory(impl: UserDataHelperFactoryImpl): UserDataHelperFactory
}
