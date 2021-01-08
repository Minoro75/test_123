package io.minoro75.test_123.ui.repository

import io.minoro75.test_123.ui.data.entities.P24Response
import io.minoro75.test_123.ui.remote.ApiRemoteDataSource
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
private val apiRemoteDataSource: ApiRemoteDataSource
) {
    suspend fun getP24ExchangeRates(date:String):P24Response{
        return apiRemoteDataSource.getP24ExchangeRates(date)
    }
}