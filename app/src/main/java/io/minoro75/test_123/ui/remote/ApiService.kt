package io.minoro75.test_123.ui.remote

import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.data.entities.P24Response
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //privat24 request format (dd.mm.yyyy)
    @GET("p24api/exchange_rates?json")
    suspend fun getCurrencyExchangeP24(@Query("date")date:String) : P24Response

    //nbu request format (yyyymmdd)
    @GET("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date={yyyy}{mm}{dd}&json")
    suspend fun getCurrencyExchangeNBU(@Path("yyyy")yyyy: Int,
                                       @Path("mm")mm: Int,
                                       @Path("dd")dd: Int) : NbuResponse
}