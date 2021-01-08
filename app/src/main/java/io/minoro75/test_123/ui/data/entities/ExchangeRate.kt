package io.minoro75.test_123.ui.data.entities

data class ExchangeRate(
    val baseCurrency: String,
    val currency: String,
    val purchaseRate: Double,
    val purchaseRateNB: Double,
    val saleRate: Double,
    val saleRateNB: Double
)