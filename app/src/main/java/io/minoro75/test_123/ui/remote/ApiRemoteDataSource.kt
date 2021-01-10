package io.minoro75.test_123.ui.remote

import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.data.entities.P24Response
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) :
	ApiServiceHelper {

	override suspend fun getP24ExchangeRates(date: String): P24Response {
		return apiService.getCurrencyExchangeP24(date)
	}

	override suspend fun getNbuExchangeRates(date: String): NbuResponse {
		return apiService.getCurrencyExchangeNBU(date)
	}
}