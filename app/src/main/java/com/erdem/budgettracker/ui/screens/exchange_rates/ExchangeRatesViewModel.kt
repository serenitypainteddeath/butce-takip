package com.erdem.budgettracker.ui.screens.exchange_rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdem.budgettracker.data.model.ExchangeRateState
import com.erdem.budgettracker.data.repository.ExchangeRateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(
    private val repository: ExchangeRateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExchangeRateState())
    val uiState: StateFlow<ExchangeRateState> = _uiState.asStateFlow()

    init {
        loadPopularCurrencies()
    }

    fun loadPopularCurrencies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.getPopularCurrencies()
                .onSuccess { rates ->
                    val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(Date())
                    _uiState.value = ExchangeRateState(
                        rates = rates,
                        isLoading = false,
                        error = null,
                        lastUpdated = currentTime
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Bilinmeyen hata"
                    )
                }
        }
    }

    fun loadAllCurrencies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.getLatestRates("TRY")
                .onSuccess { rates ->
                    val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(Date())
                    _uiState.value = ExchangeRateState(
                        rates = rates,
                        isLoading = false,
                        error = null,
                        lastUpdated = currentTime
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Bilinmeyen hata"
                    )
                }
        }
    }

    fun refresh() {
        loadPopularCurrencies()
    }
} 