package com.shahzadian.recoverycalculator.domain.factory

import com.shahzadian.recoverycalculator.domain.HealthRecoveryCalculator
import com.shahzadian.recoverycalculator.domain.HealthRecoveryCalculatorImpl
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import javax.inject.Inject

interface HealthRecoveryCalculatorFactory {

    /**
     * @throws IllegalStateException if user is not registered
     */
    suspend fun create(): HealthRecoveryCalculator
}

internal class HealthRecoveryCalculatorFactoryImpl @Inject constructor(
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : HealthRecoveryCalculatorFactory {

    override suspend fun create(): HealthRecoveryCalculator {
        val userData = getUserDataUseCase() ?: throw IllegalStateException("User is not registered")
        val userDataHelper = userDataHelperFactory.create(userData)
        return HealthRecoveryCalculatorImpl(
            userData = userData,
            userDataHelper = userDataHelper
        )
    }
}
