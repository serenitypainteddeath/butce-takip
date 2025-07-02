package com.erdem.budgettracker.ui.screens.add_transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdem.budgettracker.data.model.*
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedCategory by remember { mutableStateOf(getExpenseCategories().first()) }
    var showError by remember { mutableStateOf(false) }

    // Tip değiştiğinde kategoriyi sıfırla
    LaunchedEffect(selectedType) {
        selectedCategory = when (selectedType) {
            TransactionType.INCOME -> getIncomeCategories().first()
            TransactionType.EXPENSE -> getExpenseCategories().first()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yeni İşlem") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // İşlem Tipi Seçimi - Card yapısı
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "İşlem Tipi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Gelir Kartı
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedType == TransactionType.INCOME) 
                                    MaterialTheme.colorScheme.primaryContainer 
                                else 
                                    MaterialTheme.colorScheme.surface
                            ),
                            onClick = { selectedType = TransactionType.INCOME }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AttachMoney,
                                    contentDescription = "Gelir",
                                    tint = if (selectedType == TransactionType.INCOME) 
                                        MaterialTheme.colorScheme.onPrimaryContainer 
                                    else 
                                        MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Gelir",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (selectedType == TransactionType.INCOME) 
                                        MaterialTheme.colorScheme.onPrimaryContainer 
                                    else 
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        
                        // Gider Kartı
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedType == TransactionType.EXPENSE) 
                                    MaterialTheme.colorScheme.errorContainer 
                                else 
                                    MaterialTheme.colorScheme.surface
                            ),
                            onClick = { selectedType = TransactionType.EXPENSE }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoneyOff,
                                    contentDescription = "Gider",
                                    tint = if (selectedType == TransactionType.EXPENSE) 
                                        MaterialTheme.colorScheme.onErrorContainer 
                                    else 
                                        MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Gider",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (selectedType == TransactionType.EXPENSE) 
                                        MaterialTheme.colorScheme.onErrorContainer 
                                    else 
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Kategori Seçimi - Card yapısı
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Kategori",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    CategorySelector(
                        selectedCategory = selectedCategory,
                        selectedType = selectedType,
                        onCategorySelected = { selectedCategory = it }
                    )
                }
            }

            // Tutar ve Açıklama Girişi - Card yapısı
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "İşlem Detayları",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Tutar Girişi
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { 
                            if (it.isEmpty() || it.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                                amount = it
                                showError = false
                            }
                        },
                        label = { Text("Tutar (₺)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        isError = showError,
                        supportingText = {
                            if (showError) {
                                Text("Lütfen geçerli bir tutar girin")
                            }
                        }
                    )

                    // Açıklama Girişi
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Açıklama") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3
                    )
                }
            }

            // Kaydet Butonu
            Button(
                onClick = {
                    val amountValue = amount.toDoubleOrNull()
                    if (amountValue != null && amountValue > 0 && description.isNotBlank()) {
                        viewModel.addTransaction(
                            amount = amountValue,
                            description = description,
                            type = selectedType,
                            category = selectedCategory
                        )
                        onNavigateBack()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (selectedType) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.primaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.errorContainer
                    },
                    contentColor = when (selectedType) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onPrimaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                    }
                )
            ) {
                Text(
                    text = "İşlemi Kaydet",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    selectedCategory: Category,
    selectedType: TransactionType,
    onCategorySelected: (Category) -> Unit
) {
    val categories = when (selectedType) {
        TransactionType.INCOME -> getIncomeCategories()
        TransactionType.EXPENSE -> getExpenseCategories()
    }
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in categories.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // İlk kategori
                FilterChip(
                    selected = selectedCategory == categories[i],
                    onClick = { onCategorySelected(categories[i]) },
                    label = {
                        Text(getCategoryDisplayName(categories[i]))
                    },
                    modifier = Modifier.weight(1f)
                )
                
                // İkinci kategori (eğer varsa)
                if (i + 1 < categories.size) {
                    FilterChip(
                        selected = selectedCategory == categories[i + 1],
                        onClick = { onCategorySelected(categories[i + 1]) },
                        label = {
                            Text(getCategoryDisplayName(categories[i + 1]))
                        },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // Boş alan bırak
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
} 