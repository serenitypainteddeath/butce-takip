package com.erdem.budgettracker.ui.screens.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdem.budgettracker.data.local.TransactionDao
import com.erdem.budgettracker.data.model.Category
import com.erdem.budgettracker.data.model.Transaction
import com.erdem.budgettracker.data.model.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel() {

    fun addTransaction(
        amount: Double,
        description: String,
        type: TransactionType,
        category: Category
    ) {
        viewModelScope.launch {
            val transaction = Transaction(
                amount = amount,
                description = description,
                type = type,
                category = category,
                date = Date()
            )
            transactionDao.insertTransaction(transaction)
        }
    }
} 