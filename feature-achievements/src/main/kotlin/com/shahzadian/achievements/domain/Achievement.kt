package com.shahzadian.achievements.domain

import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import java.util.Currency

class Achievement(
    val id: String,
    val name: TextWrapper,
    val description: TextWrapper,
    val icon: ImageWrapper,
    val achievementItem: AchievementItem,
)

sealed interface AchievementItem {

    class Cigs(
        val necessaryCount: Int,
    ) : AchievementItem

    class Days(
        val necessaryCount: Int,
    ) : AchievementItem

    class Money(
        val necessaryAmount: Float,
        val currency: Currency,
    ) : AchievementItem {

        fun getDisplayableAmount(): String {
            val currency = currency.symbol ?: ""
            return "$necessaryAmount$currency"
        }
    }
}

sealed interface AchievementState {
    object Unlocked : AchievementState
    class Locked(
        val percentsReached: Float
    ) : AchievementState
}
