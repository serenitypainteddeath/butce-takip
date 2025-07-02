package com.erdem.budgettracker.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double>
)

data class CurrencyRate(
    val code: String,
    val name: String,
    val rate: Double,
    val symbol: String
) {
    companion object {
        fun getCurrencyName(code: String): String = when (code) {
            "USD" -> "Amerikan Doları"
            "EUR" -> "Euro"
            "GBP" -> "İngiliz Sterlini"
            "JPY" -> "Japon Yeni"
            "CHF" -> "İsviçre Frangı"
            "CAD" -> "Kanada Doları"
            "AUD" -> "Avustralya Doları"
            "CNY" -> "Çin Yuanı"
            "RUB" -> "Rus Rublesi"
            "SAR" -> "Suudi Arabistan Riyali"
            "TRY" -> "Türk Lirası"
            else -> code
        }

        fun getCurrencySymbol(code: String): String = when (code) {
            "USD" -> "$"
            "EUR" -> "€"
            "GBP" -> "£"
            "JPY" -> "¥"
            "CHF" -> "CHF"
            "CAD" -> "C$"
            "AUD" -> "A$"
            "CNY" -> "¥"
            "RUB" -> "₽"
            "SAR" -> "ر.س"
            "TRY" -> "₺"
            else -> code
        }
    }
}

data class ExchangeRateState(
    val rates: List<CurrencyRate> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastUpdated: String = ""
) 