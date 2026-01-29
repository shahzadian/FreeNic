package com.shahzadian.ad

import android.app.Activity
import android.content.Context
import com.freenic.ui.appinfo.AppInfoHelper
import com.freenic.ui.extensions.lazyUnsafe
import com.freenic.ui.model.CoroutineContexts
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.shahzadian.subscription.domain.GetUserCurrentSubscriptionUseCase
import com.shahzadian.subscription.domain.UserSubscriptionStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AdHelper {

    suspend fun initialize(context: Context)

    // Единый поток запросов на показ с указанием типа объявления
    val adShowRequestFlow: Flow<AdType>

    // Запросить показ объявления определенного типа
    fun requestAdShow(adType: AdType = AdType.Interstitial)

    // Показ объявления указанного типа (по умолчанию Interstitial)
    suspend fun showAd(
        activity: Activity,
        adType: AdType = AdType.Interstitial
    ): Flow<BannerAdShowResult>

    // Загрузка объявления указанного типа (по умолчанию Interstitial)
    suspend fun loadBannerAd(adType: AdType = AdType.Interstitial): Flow<BannerAdLoadResult>

    sealed interface BannerAdLoadResult {
        object Success : BannerAdLoadResult

        class Error(
            val throwable: Throwable
        ) : BannerAdLoadResult
    }

    sealed interface BannerAdShowResult {
        val adType: AdType

        data class Showing(
            override val adType: AdType,
        ) : BannerAdShowResult

        data class Dismissed(
            override val adType: AdType,
        ) : BannerAdShowResult

        data class Error(
            override val adType: AdType,
            val throwable: Throwable
        ) : BannerAdShowResult

        data class Rewarded(
            override val adType: AdType,
        ) : BannerAdShowResult
    }

    enum class AdType {
        Interstitial,
        Rewarded,
        RewardedInterstitial
    }
}

internal class AdHelperImpl @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
    private val appInfoHelper: AppInfoHelper,
    private val coroutineContexts: CoroutineContexts,
    private val getUserCurrentSubscriptionUseCase: GetUserCurrentSubscriptionUseCase,
) : AdHelper {

    private var currentInterstitialAd: InterstitialAd? = null
    private var currentRewardedAd: RewardedAd? = null
    private var currentRewardedInterstitialAd: RewardedInterstitialAd? = null

    private var lastTimeAdWasShown: Long? = null

    @Volatile
    private var isLoadingAd: Boolean = false

    @Volatile
    private var isLoadingRewardedAd: Boolean = false

    @Volatile
    private var isLoadingRewardedInterstitialAd: Boolean = false

    override val adShowRequestFlow: MutableSharedFlow<AdHelper.AdType> by lazyUnsafe {
        MutableSharedFlow()
    }

    private val coroutineScope by lazyUnsafe {
        CoroutineScope(coroutineContexts.unconfined)
    }

    override fun requestAdShow(adType: AdHelper.AdType) {
        coroutineScope.launch {
            if (!canShowAdToUser(adType)) return@launch
            when (adType) {
                AdHelper.AdType.Interstitial -> {
                    if (currentInterstitialAd != null) {
                        adShowRequestFlow.emit(adType)
                    } else {
                        withContext(coroutineContexts.main) {
                            loadBannerAd(adType).collect { }
                        }
                    }
                }

                AdHelper.AdType.Rewarded -> {
                    if (currentRewardedAd != null) {
                        adShowRequestFlow.emit(adType)
                    } else {
                        withContext(coroutineContexts.main) {
                            loadBannerAd(adType).collect { }
                        }
                    }
                }

                AdHelper.AdType.RewardedInterstitial -> {
                    if (currentRewardedInterstitialAd != null) {
                        adShowRequestFlow.emit(adType)
                    } else {
                        withContext(coroutineContexts.main) {
                            loadBannerAd(adType).collect { }
                        }
                    }
                }
            }
        }
    }

    private fun showInterstitialAd(activity: Activity): Flow<AdHelper.BannerAdShowResult> {
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.Interstitial)) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.Interstitial,
                        throwable = IllegalStateException("Cannot show ad currently")
                    )
                )
                close()
                return@callbackFlow
            }
            val ad = currentInterstitialAd
            if (ad == null) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.Interstitial,
                        throwable = IllegalStateException("Ad is not loaded yet")
                    )
                )
                close()
                return@callbackFlow
            }

            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    currentInterstitialAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Dismissed(
                            adType = AdHelper.AdType.Interstitial,
                        )
                    )
                    lastTimeAdWasShown = System.currentTimeMillis()
                    close()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    currentInterstitialAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Error(
                            adType = AdHelper.AdType.Interstitial,
                            throwable = Throwable(p0.message)
                        )
                    )
                    close()
                }

                override fun onAdShowedFullScreenContent() {
                    trySend(
                        AdHelper.BannerAdShowResult.Showing(
                            adType = AdHelper.AdType.Interstitial,
                        )
                    )
                }
            }

            ad.show(activity)

            awaitClose {
                // Avoid memory leaks
                ad.fullScreenContentCallback = null
            }
        }
    }

    override suspend fun showAd(
        activity: Activity,
        adType: AdHelper.AdType
    ): Flow<AdHelper.BannerAdShowResult> {
        return when (adType) {
            AdHelper.AdType.Interstitial -> showInterstitialAd(activity)
            AdHelper.AdType.Rewarded -> showRewardedAd(activity)
            AdHelper.AdType.RewardedInterstitial -> showRewardedInterstitialAd(activity)
        }
    }

    private fun showRewardedAd(activity: Activity): Flow<AdHelper.BannerAdShowResult> {
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.Rewarded)) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.Rewarded,
                        throwable = IllegalStateException("Cannot show ad currently")
                    )
                )
                close()
                return@callbackFlow
            }
            val ad = currentRewardedAd
            if (ad == null) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.Rewarded,
                        throwable = IllegalStateException("Ad is not loaded yet")
                    )
                )
                close()
                return@callbackFlow
            }

            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    currentRewardedAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Dismissed(
                            adType = AdHelper.AdType.Rewarded,
                        )
                    )
                    lastTimeAdWasShown = System.currentTimeMillis()
                    close()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    currentRewardedAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Error(
                            adType = AdHelper.AdType.Rewarded,
                            throwable = Throwable(p0.message)
                        )
                    )
                    close()
                }

                override fun onAdShowedFullScreenContent() {
                    trySend(
                        AdHelper.BannerAdShowResult.Showing(
                            adType = AdHelper.AdType.Rewarded,
                        )
                    )
                }
            }

            ad.show(activity) { _: RewardItem ->
                trySend(
                    AdHelper.BannerAdShowResult.Rewarded(
                        adType = AdHelper.AdType.Rewarded,
                    )
                )
            }

            awaitClose {
                ad.fullScreenContentCallback = null
            }
        }
    }

    private fun showRewardedInterstitialAd(activity: Activity): Flow<AdHelper.BannerAdShowResult> {
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.RewardedInterstitial)) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.RewardedInterstitial,
                        throwable = IllegalStateException("Cannot show ad currently")
                    )
                )
                close()
                return@callbackFlow
            }
            val ad = currentRewardedInterstitialAd
            if (ad == null) {
                trySend(
                    AdHelper.BannerAdShowResult.Error(
                        adType = AdHelper.AdType.RewardedInterstitial,
                        throwable = IllegalStateException("Ad is not loaded yet")
                    )
                )
                close()
                return@callbackFlow
            }

            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    currentRewardedInterstitialAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Dismissed(
                            adType = AdHelper.AdType.RewardedInterstitial,
                        )
                    )
                    lastTimeAdWasShown = System.currentTimeMillis()
                    close()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    currentRewardedInterstitialAd = null
                    trySend(
                        AdHelper.BannerAdShowResult.Error(
                            adType = AdHelper.AdType.RewardedInterstitial,
                            throwable = Throwable(p0.message)
                        )
                    )
                    close()
                }

                override fun onAdShowedFullScreenContent() {
                    trySend(
                        AdHelper.BannerAdShowResult.Showing(
                            adType = AdHelper.AdType.RewardedInterstitial,
                        )
                    )
                }
            }

            ad.show(activity) { _: RewardItem ->
                trySend(
                    AdHelper.BannerAdShowResult.Rewarded(
                        adType = AdHelper.AdType.RewardedInterstitial,
                    )
                )
            }

            awaitClose {
                ad.fullScreenContentCallback = null
            }
        }
    }

    private suspend fun loadInterstitialAd(): Flow<AdHelper.BannerAdLoadResult> {
        if (isLoadingAd) return callbackFlow { close() }
        isLoadingAd = true
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.Interstitial)) {
                trySend(AdHelper.BannerAdLoadResult.Error(IllegalStateException("Cannot show ad currently")))
                isLoadingAd = false
                close()
                return@callbackFlow
            }
            InterstitialAd.load(
                context,
                getAppAdId(AdHelper.AdType.Interstitial),
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(p0: InterstitialAd) {
                        currentInterstitialAd = p0
                        trySend(AdHelper.BannerAdLoadResult.Success)
                        isLoadingAd = false
                        close()
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        currentInterstitialAd = null
                        trySend(
                            AdHelper.BannerAdLoadResult.Error(
                                Throwable(p0.message)
                            )
                        )
                        isLoadingAd = false
                        close()
                    }
                }
            )

            awaitClose {
                isLoadingAd = false
            }
        }
    }

    override suspend fun loadBannerAd(adType: AdHelper.AdType): Flow<AdHelper.BannerAdLoadResult> {
        return when (adType) {
            AdHelper.AdType.Interstitial -> loadInterstitialAd()
            AdHelper.AdType.Rewarded -> loadRewardedAd()
            AdHelper.AdType.RewardedInterstitial -> loadRewardedInterstitialAd()
        }
    }

    private suspend fun loadRewardedAd(): Flow<AdHelper.BannerAdLoadResult> {
        if (isLoadingRewardedAd) return callbackFlow { close() }
        isLoadingRewardedAd = true
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.Rewarded)) {
                trySend(AdHelper.BannerAdLoadResult.Error(IllegalStateException("Cannot show ad currently")))
                isLoadingRewardedAd = false
                close()
                return@callbackFlow
            }
            RewardedAd.load(
                context,
                getAppAdId(AdHelper.AdType.Rewarded),
                AdRequest.Builder().build(),
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        currentRewardedAd = null
                        trySend(AdHelper.BannerAdLoadResult.Error(Throwable(loadAdError.message)))
                        isLoadingRewardedAd = false
                        close()
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        currentRewardedAd = ad
                        trySend(AdHelper.BannerAdLoadResult.Success)
                        isLoadingRewardedAd = false
                        close()
                    }
                }
            )

            awaitClose {
                isLoadingRewardedAd = false
            }
        }
    }

    private suspend fun loadRewardedInterstitialAd(): Flow<AdHelper.BannerAdLoadResult> {
        if (isLoadingRewardedInterstitialAd) return callbackFlow { close() }
        isLoadingRewardedInterstitialAd = true
        return callbackFlow {
            if (!canShowAdToUser(adType = AdHelper.AdType.RewardedInterstitial)) {
                trySend(AdHelper.BannerAdLoadResult.Error(IllegalStateException("Cannot show ad currently")))
                isLoadingRewardedInterstitialAd = false
                close()
                return@callbackFlow
            }
            RewardedInterstitialAd.load(
                context,
                getAppAdId(AdHelper.AdType.RewardedInterstitial),
                AdRequest.Builder().build(),
                object : RewardedInterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        currentRewardedInterstitialAd = null
                        trySend(AdHelper.BannerAdLoadResult.Error(Throwable(loadAdError.message)))
                        isLoadingRewardedInterstitialAd = false
                        close()
                    }

                    override fun onAdLoaded(ad: RewardedInterstitialAd) {
                        currentRewardedInterstitialAd = ad
                        trySend(AdHelper.BannerAdLoadResult.Success)
                        isLoadingRewardedInterstitialAd = false
                        close()
                    }
                }
            )

            awaitClose {
                isLoadingRewardedInterstitialAd = false
            }
        }
    }

    override suspend fun initialize(context: Context) {
        MobileAds.initialize(context)
    }

    private suspend fun canShowAdToUser(adType: AdHelper.AdType): Boolean {
        if (adType == AdHelper.AdType.Interstitial) {
            if (lastTimeAdWasShown != null && System.currentTimeMillis() - lastTimeAdWasShown!! < AD_SHOW_MINIMAL_INTERVAL_MILLIS) {
                return false
            }
        }
        return getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.NoSubscription
    }

    private fun getAppAdId(adType: AdHelper.AdType = AdHelper.AdType.Interstitial): String {
        if (appInfoHelper.isDebugBuild()) {
            return when (adType) {
                AdHelper.AdType.Interstitial -> ADS_TEST_ID
                AdHelper.AdType.Rewarded -> ADS_TEST_REWARDED_ID
                AdHelper.AdType.RewardedInterstitial -> ADS_TEST_REWARDED_INTERSTITIAL_ID
            }
        }
        // Prod: пока используем один и тот же ID, до передачи отдельных ID
        return ADS_PROD_ID
    }

    private companion object {
        const val ADS_TEST_ID: String = "ca-app-pub-3940256099942544/1033173712"
        const val ADS_TEST_REWARDED_ID: String = "ca-app-pub-3940256099942544/5224354917"
        const val ADS_TEST_REWARDED_INTERSTITIAL_ID: String =
            "ca-app-pub-3940256099942544/5354046379"
        const val ADS_PROD_ID: String = "ca-app-pub-3104359766953553/8805483242"
        const val AD_SHOW_MINIMAL_INTERVAL_MILLIS: Long = 1000 * 60 * 2
    }
}