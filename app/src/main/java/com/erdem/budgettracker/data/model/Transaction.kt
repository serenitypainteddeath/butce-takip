package com.erdem.budgettracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class TransactionType {
    INCOME,
    EXPENSE
}

enum class Category {
    // Gider kategorileri
    FOOD,
    TRANSPORT,
    RENT,
    UTILITIES,
    ENTERTAINMENT,
    SHOPPING,
    HEALTH,
    EDUCATION,
    OTHER,
    
    // Gelir kategorileri
    SALARY,
    BONUS,
    FREELANCE,
    INVESTMENT,
    BUSINESS,
    GIFT,
    RENTAL_INCOME,
    OTHER_INCOME
}

// Kategorileri türe göre filtrelemek için yardımcı fonksiyonlar
fun getIncomeCategories(): List<Category> {
    return listOf(
        Category.SALARY,
        Category.BONUS,
        Category.FREELANCE,
        Category.INVESTMENT,
        Category.BUSINESS,
        Category.GIFT,
        Category.RENTAL_INCOME,
        Category.OTHER_INCOME
    )
}

fun getExpenseCategories(): List<Category> {
    return listOf(
        Category.FOOD,
        Category.TRANSPORT,
        Category.RENT,
        Category.UTILITIES,
        Category.ENTERTAINMENT,
        Category.SHOPPING,
        Category.HEALTH,
        Category.EDUCATION,
        Category.OTHER
    )
}

fun getCategoryDisplayName(category: Category): String {
    return when (category) {
        // Gider kategorileri
        Category.FOOD -> "Yiyecek"
        Category.TRANSPORT -> "Ulaşım"
        Category.RENT -> "Kira"
        Category.UTILITIES -> "Faturalar"
        Category.ENTERTAINMENT -> "Eğlence"
        Category.SHOPPING -> "Alışveriş"
        Category.HEALTH -> "Sağlık"
        Category.EDUCATION -> "Eğitim"
        Category.OTHER -> "Diğer"
        
        // Gelir kategorileri
        Category.SALARY -> "Maaş"
        Category.BONUS -> "Bonus"
        Category.FREELANCE -> "Serbest Çalışma"
        Category.INVESTMENT -> "Yatırım"
        Category.BUSINESS -> "İş"
        Category.GIFT -> "Hediye"
        Category.RENTAL_INCOME -> "Kira Geliri"
        Category.OTHER_INCOME -> "Diğer Gelir"
    }
}

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val type: TransactionType,
    val category: Category,
    val date: Date,
    val createdAt: Date = Date()
) 