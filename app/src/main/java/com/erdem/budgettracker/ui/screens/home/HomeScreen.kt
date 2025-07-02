package com.erdem.budgettracker.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdem.budgettracker.data.model.Transaction
import com.erdem.budgettracker.data.model.TransactionType
import com.erdem.budgettracker.data.model.getCategoryDisplayName
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddTransaction: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val transactions by viewModel.transactions.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Özet Kartları
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryCard(
                title = "Toplam Gelir",
                amount = totalIncome,
                type = TransactionType.INCOME,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Toplam Gider",
                amount = totalExpense,
                type = TransactionType.EXPENSE,
                modifier = Modifier.weight(1f)
            )
        }

        // Bakiye Kartı
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Güncel Bakiye",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = formatCurrency(totalIncome - totalExpense),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // İşlem Listesi
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz işlem bulunmuyor.\nYeni bir işlem eklemek için + butonuna tıklayın.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onDelete = { viewModel.deleteTransaction(it) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryCard(
    title: String,
    amount: Double,
    type: TransactionType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = when (type) {
                TransactionType.INCOME -> MaterialTheme.colorScheme.tertiaryContainer
                TransactionType.EXPENSE -> MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = when (type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                }
            )
            Text(
                text = formatCurrency(amount),
                style = MaterialTheme.typography.headlineSmall,
                color = when (type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(
    transaction: Transaction,
    onDelete: (Transaction) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (transaction.type) {
                TransactionType.INCOME -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                TransactionType.EXPENSE -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.titleMedium,
                    color = when (transaction.type) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                    }
                )
                Text(
                    text = getCategoryDisplayName(transaction.category),
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (transaction.type) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.7f)
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatCurrency(transaction.amount),
                    style = MaterialTheme.typography.titleMedium,
                    color = when (transaction.type) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                    }
                )
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Sil",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("İşlemi Sil") },
            text = { Text("Bu işlemi silmek istediğinizden emin misiniz?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(transaction)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Sil")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("tr", "TR")).format(amount)
} 