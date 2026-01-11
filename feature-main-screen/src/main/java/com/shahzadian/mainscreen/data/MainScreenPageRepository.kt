package com.shahzadian.mainscreen.data

import com.freenic.ui.AppScreen
import com.freenic.ui.wrapper.ImageWrapper
import com.freenic.ui.wrapper.TextWrapper
import com.shahzadian.baseui.R
import com.shahzadian.mainscreen.model.MainScreenPage
import javax.inject.Inject

internal interface MainScreenPageRepository {

    suspend fun getMainScreenPages(): List<MainScreenPage>
}

internal class MainScreenPageRepositoryImpl @Inject constructor() : MainScreenPageRepository {

    override suspend fun getMainScreenPages(): List<MainScreenPage> {
        return listOf(
            MainScreenPage(
                name = TextWrapper.FromRes(
                    R.string.main_page_home_name
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.mainscreen.R.drawable.ic_main_page_home
                ),
                screen = AppScreen.JourneyCalendarScreen
            ),
            MainScreenPage(
                name = TextWrapper.FromRes(
                    R.string.main_page_cravings_name
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.mainscreen.R.drawable.ic_main_page_cravings
                ),
                screen = AppScreen.CravingsScreen
            ),
            MainScreenPage(
                name = TextWrapper.FromRes(
                    R.string.main_page_goals_name
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.mainscreen.R.drawable.ic_main_page_goals
                ),
                screen = AppScreen.GoalsScreen
            ),
            MainScreenPage(
                name = TextWrapper.FromRes(
                    R.string.main_page_profile_name
                ),
                icon = ImageWrapper.FromRes(
                    com.shahzadian.mainscreen.R.drawable.ic_main_page_profile
                ),
                screen = AppScreen.ProfileScreen
            )
        )
    }
}
