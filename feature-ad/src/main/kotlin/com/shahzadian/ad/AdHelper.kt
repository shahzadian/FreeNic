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

    val adShowRequestFlow: Flow<Unit>

    fun requestAdShow()

    suspend fun showAd(activity: Activity): Flow<BannerAdShowResult>

    suspend fun loadBannerAd(): Flow<BannerAdLoadResult>

    sealed interface BannerAdLoadResult {
        object Success : BannerAdLoadResult

        class Error(
            val throwable: Throwable
        ) : BannerAdLoadResult
    }

    sealed interface BannerAdShowResult {
        object Showing : BannerAdShowResult
        object Dismissed : BannerAdShowResult
        class Error(
            val throwable: Throwable
        ) : BannerAdShowResult
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

    private var lastTimeAdWasShown: Long? = null

    @Volatile
    private var isLoadingAd: Boolean = false

    override val adShowRequestFlow: MutableSharedFlow<Unit> by lazyUnsafe {
        MutableSharedFlow()
    }

    private val coroutineScope by lazyUnsafe {
        CoroutineScope(coroutineContexts.unconfined)
    }

    override fun requestAdShow() {
        coroutineScope.launch {
            if (!canShowAdToUser()) return@launch
            if (currentInterstitialAd != null) {
                adShowRequestFlow.emit(Unit)
            } else {
                withContext(coroutineContexts.main) {
                    loadBannerAd()
                        .collect { result ->
//                        Result is not needed
                        }
                }
            }
        }
    }

    override suspend fun showAd(activity: Activity): Flow<AdHelper.BannerAdShowResult> {
        return callbackFlow {
            if (!canShowAdToUser()) {
                trySend(AdHelper.BannerAdShowResult.Error(IllegalStateException("Cannot show ad currently")))
                close()
                return@callbackFlow
            }
            val ad = currentInterstitialAd
            if (ad == null) {
                trySend(AdHelper.BannerAdShowResult.Error(IllegalStateException("Ad is not loaded yet")))
                close()
                return@callbackFlow
            }

            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    currentInterstitialAd = null
                    trySend(AdHelper.BannerAdShowResult.Dismissed)
                    lastTimeAdWasShown = System.currentTimeMillis()
                    close()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    currentInterstitialAd = null
                    trySend(AdHelper.BannerAdShowResult.Error(Throwable(p0.message)))
                    close()
                }

                override fun onAdShowedFullScreenContent() {
                    trySend(AdHelper.BannerAdShowResult.Showing)
                }
            }

            ad.show(activity)

            awaitClose {
                // Avoid memory leaks
                ad.fullScreenContentCallback = null
            }
        }
    }

    override suspend fun loadBannerAd(): Flow<AdHelper.BannerAdLoadResult> {
        if (isLoadingAd) return callbackFlow { close() }
        isLoadingAd = true
        return callbackFlow {
            if (!canShowAdToUser()) {
                trySend(AdHelper.BannerAdLoadResult.Error(IllegalStateException("Cannot show ad currently")))
                isLoadingAd = false
                close()
                return@callbackFlow
            }
            InterstitialAd.load(
                context,
                getAppAdId(),
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

    override suspend fun initialize(context: Context) {
        MobileAds.initialize(context)
    }

    private suspend fun canShowAdToUser(): Boolean {
        if (lastTimeAdWasShown != null && System.currentTimeMillis() - lastTimeAdWasShown!! < AD_SHOW_MINIMAL_INTERVAL_MILLIS) {
            return false
        }
        return getUserCurrentSubscriptionUseCase() is UserSubscriptionStatus.NoSubscription
    }

    private fun getAppAdId(): String {
        return if (appInfoHelper.isDebugBuild()) {
            ADS_TEST_ID
        } else {
            ADS_PROD_ID
        }
    }

    private companion object {
        const val ADS_TEST_ID: String = "ca-app-pub-3940256099942544/1033173712"
        const val ADS_PROD_ID: String = "ca-app-pub-3104359766953553/8805483242"
        const val AD_SHOW_MINIMAL_INTERVAL_MILLIS: Long = 1000 * 60
    }
}