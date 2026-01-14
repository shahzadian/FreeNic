package com.shahzadian.freenic.navigation

import androidx.navigation.NavController
import com.freenic.navigation.AppNavigator
import javax.inject.Inject

interface AppNavigatorFactory {

    fun create(
        navController: NavController,
    ): AppNavigator
}

internal class AppNavigatorFactoryImpl @Inject constructor() : AppNavigatorFactory {

    override fun create(navController: NavController): AppNavigator {
        return AppNavigatorImpl(
            navController = navController,
        )
    }
}
