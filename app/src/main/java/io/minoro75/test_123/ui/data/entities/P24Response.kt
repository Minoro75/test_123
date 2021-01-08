package io.minoro75.test_123.ui.data.entities

data class P24Response(
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val date: String,
    val exchangeRate: List<ExchangeRate>
)