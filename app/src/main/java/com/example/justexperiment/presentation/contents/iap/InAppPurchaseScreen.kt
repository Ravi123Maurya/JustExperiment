package com.example.justexperiment.presentation.contents.iap

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.example.justexperiment.presentation.common.ContentView
import com.example.justexperiment.presentation.common.JustCard
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.heightSpacer
import com.example.justexperiment.presentation.utils.showToast
import kotlinx.coroutines.launch

val inAppPurchase = Content(
    id = 653863,
    title = "In App Purchase",
    description = "In‑app purchases (IAP) on Android are powered by Google Play Billing, and they let you sell digital goods directly inside your app—like premium features, subscriptions, or consumables (coins, credits, etc.).",
    icon = Icons.Rounded.Money,
    color = Color.Green,
    route = Route.InAppPurchaseScreen(653863)
)

@Composable
fun InAppPurchaseScreen(
    id: Int,
    navController: NavController,
) {

    ContentView(id, { navController.popBackStack() }) {
        IapScreenContent()
    }

}

@Composable
private fun IapScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JustOneTimePurchase()
    }
}

// In InAppPurchaseScreen.kt

// In InAppPurchaseScreen.kt

@Composable
private fun JustOneTimePurchase(
    billingManager: BillingManager = viewModel()
) {
    val context = LocalContext.current
    val productDetails by billingManager.productDetails.collectAsState()
    val purchaseState by billingManager.purchaseState.collectAsState()
    val ownedPurchases by billingManager.ownedPurchases.collectAsState()
    val premiumProduct = productDetails.find { it.productId == BillingManager.PREMIUM_UPGRADE }
    val hasPremium = ownedPurchases.any { it.products.contains(BillingManager.PREMIUM_UPGRADE) }

    LaunchedEffect(purchaseState) {
        when (val state = purchaseState) {
            is BillingManager.PurchaseState.Success -> context.showToast("Purchase successful!")
            is BillingManager.PurchaseState.Error -> context.showToast("Error: ${state.message}")
            else -> {}
        }
    }

    JustCard {
        if (hasPremium) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Thank you for being a premium user!",
                    textAlign = TextAlign.Center
                )

                Button(onClick = {
                    val purchaseToConsume =
                        ownedPurchases.find { it.products.contains(BillingManager.PREMIUM_UPGRADE) }
                    if (purchaseToConsume != null) {
                        billingManager.consumePurchase(purchaseToConsume)
                    }
                }) {
                    Text("Consume (for testing)")
                }
            }

        } else {
            if (premiumProduct != null) {
                Button(
                    enabled = purchaseState != BillingManager.PurchaseState.Loading,
                    onClick = {
                        billingManager.purchaseProduct(context as Activity, premiumProduct)
                    }
                ) {
                    Text("Buy ${premiumProduct.name} for ${premiumProduct.oneTimePurchaseOfferDetails?.formattedPrice}")
                }
            } else {
                Text("Loading products...")
            }

            if (purchaseState == BillingManager.PurchaseState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}




