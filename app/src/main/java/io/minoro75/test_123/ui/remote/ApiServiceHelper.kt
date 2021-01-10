package io.minoro75.test_123.ui.remote

import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.data.entities.P24Response

interface ApiServiceHelper {
	suspend fun getP24ExchangeRates(date: String): P24Response
	suspend fun getNbuExchangeRates(date: String): NbuResponse
}