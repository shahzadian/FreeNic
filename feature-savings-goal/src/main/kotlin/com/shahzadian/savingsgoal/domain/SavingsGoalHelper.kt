package com.shahzadian.savingsgoal.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapper
import com.shahzadian.user.domain.UserDataHelper
import com.shahzadian.user.domain.UserDataHelperFactory
import com.shahzadian.user.domain.getuserdata.GetUserDataUseCase
import com.shahzadian.user.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface SavingsGoalHelper {

    val eventFlow: Flow<SavingsGoalEvent>

    suspend fun getCurrentSavingsGoal(): SavingsGoal?

    suspend fun getAllCategories(): List<SavingsGoalCategory>

    suspend fun createSavingsGoal(
        name: String,
        amount: Double,
        categoryId: String,
        currentAmount: Float,
    )

    suspend fun updateSavingsGoal(
        name: String,
        amount: Float,
        categoryId: String,
        currentAmount: Float,
    )

    suspend fun deleteSavingsGoal()

    sealed interface SavingsGoalEvent {

        data class OnSavingsGoalCreated(
            val savingsGoal: SavingsGoal
        ) : SavingsGoalEvent

        data class OnSavingsGoalUpdated(
            val savingsGoal: SavingsGoal
        ) : SavingsGoalEvent

        object OnSavingsGoalDeleted : SavingsGoalEvent
    }
}

internal class SavingsGoalHelperImpl @Inject constructor(
    private val savingsGoalRepository: SavingsGoalRepository,
    private val savingsGoalCategoryRepository: SavingsGoalCategoryRepository,
    private val savingsGoalMapper: SavingsGoalMapper,
    private val userDataHelperFactory: UserDataHelperFactory,
    private val getUserDataUseCase: GetUserDataUseCase,
) : SavingsGoalHelper {

    override val eventFlow: MutableSharedFlow<SavingsGoalHelper.SavingsGoalEvent> by
    lazyUnsafe { MutableSharedFlow() }

    override suspend fun getCurrentSavingsGoal(): SavingsGoal? {
        return savingsGoalRepository
            .getActiveSavingsGoal()
            ?.let { savingsGoalMapper.fromDataToDomain(it) }
    }

    override suspend fun getAllCategories(): List<SavingsGoalCategory> {
        return savingsGoalCategoryRepository.getSavingsGoalCategories()
    }

    override suspend fun createSavingsGoal(
        name: String,
        amount: Double,
        categoryId: String,
        currentAmount: Float,
    ) {
        val domainGoal = savingsGoalMapper.createDomainModel(
            name = name,
            amount = amount.toFloat(),
            categoryId = categoryId,
            currentAmount = currentAmount,
        )
        val dataGoal = savingsGoalMapper.fromDomainToData(domainGoal)
        savingsGoalRepository.createSavingsGoal(dataGoal)
        eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalCreated(domainGoal))
    }

    override suspend fun updateSavingsGoal(
        name: String,
        amount: Float,
        categoryId: String,
        currentAmount: Float,
    ) {
        val existing = savingsGoalRepository.getActiveSavingsGoal()
        if (existing == null) {
            // Fallback: no existing goal, treat as create
            val domainGoal = savingsGoalMapper.createDomainModel(
                name = name,
                amount = amount,
                categoryId = categoryId,
                currentAmount = currentAmount,
            )
            val dataGoal = savingsGoalMapper.fromDomainToData(domainGoal)
            savingsGoalRepository.createSavingsGoal(dataGoal)
            eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalUpdated(domainGoal))
            return
        }

        val savedPerDay = getUserDataHelper().getSavedMoneyPerDay()
        val passedDays = getPassedDaysFromMillis(existing.createTimeMillis)
        // Convert user-entered current amount (today) to base initial amount stored in DataStore
        val newInitialAmount = (currentAmount - savedPerDay * passedDays).coerceAtLeast(0f)

        val updatedData = com.shahzadian.savingsgoal.data.SavingsGoalData(
            name = name,
            necessaryAmount = amount,
            currentAmount = newInitialAmount,
            createTimeMillis = existing.createTimeMillis,
            categoryId = categoryId,
        )
        savingsGoalRepository.createSavingsGoal(updatedData)
        val domainGoal = savingsGoalMapper.fromDataToDomain(updatedData)
        eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalUpdated(domainGoal))
    }

    override suspend fun deleteSavingsGoal() {
        savingsGoalRepository.deleteSavingsGoal()
        eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalDeleted)
    }

    // region helpers for user data and calculations
    private var _userData: UserData? = null
    private var _userDataHelper: UserDataHelper? = null

    private suspend fun getUserData(): UserData {
        return _userData ?: let {
            val userData = getUserDataUseCase()
                ?: throw IllegalStateException("User data is null")
            this._userData = userData
            userData
        }
    }

    private suspend fun getUserDataHelper(): UserDataHelper {
        return _userDataHelper ?: let {
            val helper = userDataHelperFactory.create(getUserData())
            this._userDataHelper = helper
            helper
        }
    }

    private fun getPassedDaysFromMillis(millis: Long): Int {
        val passedMillis = System.currentTimeMillis() - millis
        return (passedMillis / (1000 * 60 * 60 * 24)).toInt()
    }
    // endregion
}
