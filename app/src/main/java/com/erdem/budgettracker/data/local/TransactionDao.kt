package com.erdem.budgettracker.data.local

import androidx.room.*
import com.erdem.budgettracker.data.model.Transaction
import com.erdem.budgettracker.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date

data class CategoryTotal(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "total") val total: Double
)

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    fun getTransactionsByCategory(category: String): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsBetweenDates(startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    fun getTotalAmountByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Flow<Double?>

    @Query("SELECT category as category, SUM(amount) as total FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate GROUP BY category")
    fun getCategoryTotalsByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Flow<List<CategoryTotal>>
} 