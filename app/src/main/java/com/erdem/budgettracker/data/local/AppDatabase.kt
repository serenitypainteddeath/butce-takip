package com.erdem.budgettracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erdem.budgettracker.data.model.Transaction
import com.erdem.budgettracker.data.model.User
import com.erdem.budgettracker.util.Converters

@Database(
    entities = [Transaction::class, User::class], 
    version = 2, 
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
} 