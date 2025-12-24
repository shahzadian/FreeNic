package com.shahzadian.savingsgoalscreen.data

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.savingsgoalscreen.domain.SavingsGoalCategory
import javax.inject.Inject

internal interface SavingsGoalCategoryRepository {

    suspend fun getSavingsGoalCategories(): List<SavingsGoalCategory>
}

internal class SavingsGoalCategoryRepositoryImpl @Inject constructor(): SavingsGoalCategoryRepository {

    override suspend fun getSavingsGoalCategories(): List<SavingsGoalCategory> {
        return listOf(
            SavingsGoalCategory(
                id = CATEGORY_TECH,
                name = TextWrapper.FromRes(R.string.savings_create_category_tech),
                icon = ImageWrapper.FromRes(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_create_category_tech)
            ),
            SavingsGoalCategory(
                id = CATEGORY_TRAVEL,
                name = TextWrapper.FromRes(R.string.savings_create_category_travel),
                icon = ImageWrapper.FromRes(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_create_category_travel)
            ),
            SavingsGoalCategory(
                id = CATEGORY_HEALTH,
                name = TextWrapper.FromRes(R.string.savings_create_category_health),
                icon = ImageWrapper.FromRes(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_create_category_health)
            ),
            SavingsGoalCategory(
                id = CATEGORY_OTHER,
                name = TextWrapper.FromRes(R.string.savings_create_category_other),
                icon = ImageWrapper.FromRes(com.shahzadian.savingsgoalscreen.R.drawable.ic_savings_goal_create_category_other)
            ),
        )
    }

    private companion object {
        const val CATEGORY_TECH: String = "TECH"
        const val CATEGORY_TRAVEL: String = "TRAVEL"
        const val CATEGORY_HEALTH: String = "HEALTH"
        const val CATEGORY_OTHER: String = "OTHER"
    }
}