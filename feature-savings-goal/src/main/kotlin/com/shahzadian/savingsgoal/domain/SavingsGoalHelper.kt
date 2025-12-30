package com.shahzadian.savingsgoal.domain

import com.freenic.ui.extensions.lazyUnsafe
import com.shahzadian.savingsgoal.data.SavingsGoalRepository
import com.shahzadian.savingsgoal.data.category.SavingsGoalCategoryRepository
import com.shahzadian.savingsgoal.mapper.SavingsGoalMapper
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
    )

    suspend fun updateSavingsGoal(
        name: String,
        amount: Float,
        categoryId: String,
    )

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
        categoryId: String
    ) {
        val domainGoal = savingsGoalMapper.createDomainModel(
            name = name,
            amount = amount.toFloat(),
            categoryId = categoryId
        )
        val dataGoal = savingsGoalMapper.fromDomainToData(domainGoal)
        savingsGoalRepository.createSavingsGoal(dataGoal)
        eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalCreated(domainGoal))
    }

    override suspend fun updateSavingsGoal(
        name: String,
        amount: Float,
        categoryId: String,
    ) {
        val domainGoal = savingsGoalMapper.createDomainModel(
            name = name,
            amount = amount,
            categoryId = categoryId
        )
        val dataGoal = savingsGoalMapper.fromDomainToData(domainGoal)
        savingsGoalRepository.createSavingsGoal(dataGoal)
        eventFlow.emit(SavingsGoalHelper.SavingsGoalEvent.OnSavingsGoalUpdated(domainGoal))
    }
}
