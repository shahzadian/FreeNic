package com.freenic.ui.appinfo.appdatacache

interface AppDataCache {

    suspend fun getRegisteredTimeMillis(): Long?
    suspend fun hasSharedAchievement(): Boolean
    suspend fun getHasCreatedSavingsGoal(): Boolean
    suspend fun getSessionsCount(): Int
}

interface AppDataCacheUpdater {

    suspend fun setRegisteredTimeMillis(millis: Long)
    suspend fun setHasSharedAchievement(hasSharedAchievement: Boolean)
    suspend fun setHasCreatedSavingsGoal(hasCreatedSavingsGoal: Boolean)
    suspend fun setSessionsCount(sessionsCount: Int)
}
