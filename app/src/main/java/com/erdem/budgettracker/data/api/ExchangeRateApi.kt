package com.erdem.budgettracker.data.api

import com.erdem.budgettracker.data.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("latest/{base}")
    suspend fun getLatestRates(@Path("base") baseCurrency: String = "TRY"): Response<ExchangeRateResponse>
    
    @GET("latest/USD")
    suspend fun getLatestUSDRates(): Response<ExchangeRateResponse>
} 