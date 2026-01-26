package com.shahzadian.subscription.subscription

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

internal interface BillingDataSource {

    /**
     * Подробная информация об активной подписке из BillingClient (без серверной верификации).
     * [purchaseTimeMillis] берётся из [Purchase.getPurchaseTime].
     */
    data class ActiveSubscriptionInfo(
        val productId: String,
        val purchaseTimeMillis: Long,
        val isAutoRenewing: Boolean,
    )

    /** Возвращает активную подписку (productId + время покупки) или null. */
    suspend fun queryActiveSubscription(): ActiveSubscriptionInfo?

    /** Утилита для обратной совместимости: только productId. */
    suspend fun queryActiveSubscriptionProductId(): String?

    suspend fun launchBillingFlow(activity: Activity, productId: String): Boolean
}

internal class BillingDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : BillingDataSource {

    private var purchaseCallback: ((BillingResult) -> Unit)? = null

    // Обязательный listener для обновлений покупок. Для текущей задачи — минимальная обработка (логирование).
    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        purchaseCallback?.invoke(billingResult)
    }

    private fun newClient(): BillingClient =
        BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

    private suspend fun BillingClient.ensureConnected(): Boolean =
        suspendCancellableCoroutine { cont ->
            if (isReady) {
                cont.resume(true)
                return@suspendCancellableCoroutine
            }
            startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {
                    if (cont.isActive) cont.resume(false)
                }

                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    cont.resume(billingResult.responseCode == BillingClient.BillingResponseCode.OK)
                }
            })
        }

    override suspend fun queryActiveSubscription(): BillingDataSource.ActiveSubscriptionInfo? {
        val client = newClient()
        if (!client.ensureConnected()) return null

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        return suspendCancellableCoroutine { cont ->
            client.queryPurchasesAsync(params) { billingResult, purchasesList ->
                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                    cont.resume(null)
                    return@queryPurchasesAsync
                }
                val purchases: List<Purchase> = purchasesList
                val chosen =
                    purchases.firstOrNull { it.purchaseState == Purchase.PurchaseState.PURCHASED && !it.isAcknowledged }
                        ?: purchases.firstOrNull { it.purchaseState == Purchase.PurchaseState.PURCHASED }
                val productId = chosen?.products?.firstOrNull()
                if (productId == null) {
                    cont.resume(null)
                } else {
                    cont.resume(
                        BillingDataSource.ActiveSubscriptionInfo(
                            productId = productId,
                            purchaseTimeMillis = chosen.purchaseTime,
                            isAutoRenewing = chosen.isAutoRenewing,
                        )
                    )
                }
            }
        }
    }

    override suspend fun queryActiveSubscriptionProductId(): String? {
        return queryActiveSubscription()?.productId
    }

    override suspend fun launchBillingFlow(activity: Activity, productId: String): Boolean {
        val client = newClient()
        if (!client.ensureConnected()) {
            return false
        }

        // Query product details first
        val queryParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            )
            .build()
        val productDetails: ProductDetails = suspendCancellableCoroutine { cont ->
            client.queryProductDetailsAsync(queryParams) { billingResult, productDetailsList ->
                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                    cont.resume(null as ProductDetails?)
                    return@queryProductDetailsAsync
                }
                cont.resume(productDetailsList?.firstOrNull())
            }
        } ?: return false

        val offerToken =
            productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: return false
        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetails)
            .setOfferToken(offerToken)
            .build()

        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()

        return suspendCancellableCoroutine { cont ->
            purchaseCallback = { billingResult ->
                cont.resume(billingResult.responseCode == BillingClient.BillingResponseCode.OK)
            }
            client.launchBillingFlow(activity, flowParams)
        }
    }
}
