package com.erdem.budgettracker.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdem.budgettracker.data.local.TransactionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel() {

    fun deleteAllTransactions() {
        viewModelScope.launch {
            transactionDao.deleteAllTransactions()
        }
    }
} 