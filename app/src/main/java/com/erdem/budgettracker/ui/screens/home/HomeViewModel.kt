package com.erdem.budgettracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdem.budgettracker.data.local.TransactionDao
import com.erdem.budgettracker.data.model.Transaction
import com.erdem.budgettracker.data.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel() {

    private val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private val startOfMonth = calendar.apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }.time

    private val endOfMonth = calendar.apply {
        set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    }.time

    val transactions = transactionDao.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalIncome = transactionDao.getTotalAmountByTypeAndDateRange(
        type = TransactionType.INCOME,
        startDate = startOfMonth,
        endDate = endOfMonth
    ).map { it ?: 0.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    val totalExpense = transactionDao.getTotalAmountByTypeAndDateRange(
        type = TransactionType.EXPENSE,
        startDate = startOfMonth,
        endDate = endOfMonth
    ).map { it ?: 0.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(transaction)
        }
    }
} 