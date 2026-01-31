package com.example.justexperiment.presentation.contents.iap

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BillingManager(private val application: Application) : PurchasesUpdatedListener,
    AndroidViewModel(application) {

    val context = application.applicationContext
    private lateinit var billingClient: BillingClient
    private val _purchaseState = MutableStateFlow<PurchaseState>(PurchaseState.Idle)
    val purchaseState: StateFlow<PurchaseState> = _purchaseState.asStateFlow()

    private val _productDetails = MutableStateFlow<List<ProductDetails>>(emptyList())
    val productDetails: StateFlow<List<ProductDetails>> = _productDetails.asStateFlow()

    private val _ownedPurchases = MutableStateFlow<List<Purchase>>(emptyList())
    val ownedPurchases: StateFlow<List<Purchase>> = _ownedPurchases.asStateFlow()

    sealed class PurchaseState {
        object Idle : PurchaseState()
        object Loading : PurchaseState()
        data class Success(val purchase: Purchase) : PurchaseState()
        data class Error(val message: String) : PurchaseState()
    }

    init {
        viewModelScope.launch {
            setupBillingClient()
        }
    }

    private fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("Billing", "Connection established")
                    // Now that we are connected, query for products and existing purchases
                    queryProducts()
                    queryExistingPurchases()
                } else {
                    Log.e("Billing", "Connection failed: ${billingResult.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                // Handle disconnection, maybe retry connection
            }
        })
    }

    private fun queryProducts() {
        // Use your IapConstants here
        val params = QueryProductDetailsParams.newBuilder().setProductList(
            ALL_PRODUCT_IDS.map {
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(it)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            }
        ).build()

        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _productDetails.value = productDetailsList.productDetailsList
            }
        }
    }

    private fun queryExistingPurchases() {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.queryPurchasesAsync(params) { billingResult, purchasesList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _ownedPurchases.value = purchasesList
                // Process any unacknowledged purchases
                purchasesList.forEach { if (!it.isAcknowledged) handlePurchase(it) }
            }
        }
    }

    private suspend fun setupBillingClient() {
        val pendingPurchaseParams = PendingPurchasesParams.newBuilder()
            .enableOneTimeProducts()
            .build()

        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(pendingPurchaseParams) // Required for consumable products
            .build()

        if (!billingClient.isReady) {
            startConnection()
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.forEach { purchase ->
                    handlePurchase(purchase)
                }
            }

            BillingClient.BillingResponseCode.USER_CANCELED -> {
                _purchaseState.value = PurchaseState.Error("Purchase cancelled")
            }

            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                _purchaseState.value = PurchaseState.Error("Already owned")
                // Query existing purchases to restore
//                queryExistingPurchases()
            }

            else -> {
                _purchaseState.value = PurchaseState.Error(
                    "Purchase failed: ${billingResult.debugMessage}"
                )
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase)
            }
            _purchaseState.value = PurchaseState.Success(purchase)
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(params) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("Billing", "Purchase acknowledged")
            }
        }
    }

    fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(consumeParams) { billingResult, purchaseToken ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("Billing", "Purchase consumed successfully!")
                // After consuming, we need to refresh the list of owned items
                queryExistingPurchases()
            } else {
                Log.e("Billing", "Failed to consume purchase: ${billingResult.debugMessage}")
            }
        }
    }

    // Implementation continues below...


    //
    fun purchaseProduct(activity: Activity, productDetails: ProductDetails) {
        _purchaseState.value = PurchaseState.Loading

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)

        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
            _purchaseState.value = PurchaseState.Error(billingResult.debugMessage)
        }
    }


    companion object {
        const val PREMIUM_UPGRADE = "android.test.purchased"
        val ALL_PRODUCT_IDS = listOf(PREMIUM_UPGRADE)
    }
}
