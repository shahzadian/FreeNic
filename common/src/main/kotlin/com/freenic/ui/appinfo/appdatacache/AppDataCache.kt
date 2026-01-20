package com.freenic.ui.appinfo.appdatacache

interface AppDataCache {

    suspend fun getRegisteredTimeMillis(): Long?
    suspend fun hasSharedAchievement(): Boolean
    suspend fun getHasCreatedSavingsGoal(): Boolean
    suspend fun getSessionsCount(): Int
    suspend fun hasAppRated(): Boolean
    suspend fun getRateCancelMillis(): Long?
}

interface AppDataCacheUpdater {

    suspend fun setRegisteredTimeMillis(millis: Long)
    suspend fun onAchievementShared()
    suspend fun onSavingsGoalCreated()
    suspend fun setSessionsCount(sessionsCount: Int)
    suspend fun onAppRated()
    suspend fun setRateCancelMillis(millis: Long)
}
