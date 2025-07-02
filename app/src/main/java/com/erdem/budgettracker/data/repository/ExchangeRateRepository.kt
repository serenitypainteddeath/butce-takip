package com.erdem.budgettracker.data.repository

import com.erdem.budgettracker.data.api.ExchangeRateApi
import com.erdem.budgettracker.data.model.CurrencyRate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateRepository @Inject constructor(
    private val api: ExchangeRateApi
) {
    suspend fun getLatestRates(baseCurrency: String = "TRY"): Result<List<CurrencyRate>> {
        return try {
            val response = api.getLatestRates(baseCurrency)
            if (response.isSuccessful && response.body() != null) {
                val exchangeRateResponse = response.body()!!
                val currencyRates = exchangeRateResponse.rates.map { (code, rate) ->
                    CurrencyRate(
                        code = code,
                        name = CurrencyRate.getCurrencyName(code),
                        rate = rate,
                        symbol = CurrencyRate.getCurrencySymbol(code)
                    )
                }.sortedBy { it.name }
                
                Result.success(currencyRates)
            } else {
                Result.failure(Exception("Veri alınamadı: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUSDRates(): Result<List<CurrencyRate>> {
        return try {
            val response = api.getLatestUSDRates()
            if (response.isSuccessful && response.body() != null) {
                val exchangeRateResponse = response.body()!!
                val currencyRates = exchangeRateResponse.rates.map { (code, rate) ->
                    CurrencyRate(
                        code = code,
                        name = CurrencyRate.getCurrencyName(code),
                        rate = rate,
                        symbol = CurrencyRate.getCurrencySymbol(code)
                    )
                }.sortedBy { it.name }
                
                Result.success(currencyRates)
            } else {
                Result.failure(Exception("Veri alınamadı: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPopularCurrencies(): Result<List<CurrencyRate>> {
        return try {
            val response = api.getLatestRates("TRY")
            if (response.isSuccessful && response.body() != null) {
                val exchangeRateResponse = response.body()!!
                val popularCurrencies = listOf("USD", "EUR", "GBP", "JPY", "CHF", "CAD", "AUD")
                
                val currencyRates = popularCurrencies.mapNotNull { code ->
                    exchangeRateResponse.rates[code]?.let { rate ->
                        CurrencyRate(
                            code = code,
                            name = CurrencyRate.getCurrencyName(code),
                            rate = rate,
                            symbol = CurrencyRate.getCurrencySymbol(code)
                        )
                    }
                }
                
                Result.success(currencyRates)
            } else {
                Result.failure(Exception("Veri alınamadı: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 