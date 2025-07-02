package com.erdem.budgettracker.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.erdem.budgettracker.data.local.CategoryTotal
import com.erdem.budgettracker.data.model.*
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
) {
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    
    // Seçilen türe göre kategori toplamlarını al
    val categoryTotals by viewModel.getCategoryTotalsByType(selectedType).collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // İşlem Tipi Seçimi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransactionType.values().forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { selectedType = type },
                    label = {
                        Text(
                            when (type) {
                                TransactionType.INCOME -> "Gelirler"
                                TransactionType.EXPENSE -> "Giderler"
                            }
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Toplam Tutar Kartı
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (selectedType) {
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
                    text = when (selectedType) {
                        TransactionType.INCOME -> "Toplam Gelir"
                        TransactionType.EXPENSE -> "Toplam Gider"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = when (selectedType) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                    }
                )
                Text(
                    text = formatCurrency(
                        when (selectedType) {
                            TransactionType.INCOME -> totalIncome
                            TransactionType.EXPENSE -> totalExpense
                        }
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = when (selectedType) {
                        TransactionType.INCOME -> MaterialTheme.colorScheme.onTertiaryContainer
                        TransactionType.EXPENSE -> MaterialTheme.colorScheme.onErrorContainer
                    }
                )
            }
        }

        // Pasta Grafik
        if (categoryTotals.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kategori Dağılımı",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PieChart(
                        data = categoryTotals.map { categoryTotal ->
                            PieEntry(
                                categoryTotal.total.toFloat(),
                                getCategoryDisplayName(Category.valueOf(categoryTotal.category))
                            )
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bu dönem için veri bulunmuyor",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Kategori Detayları
        if (categoryTotals.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Kategori Detayları",
                        style = MaterialTheme.typography.titleMedium
                    )
                    categoryTotals.forEach { categoryTotal ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = getCategoryDisplayName(Category.valueOf(categoryTotal.category)),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = formatCurrency(categoryTotal.total),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PieChart(data: List<PieEntry>) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            com.github.mikephil.charting.charts.PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setHoleColor(Color.Transparent.value.toInt())
                setTransparentCircleRadius(0f)
                setDrawEntryLabels(false)
                legend.isEnabled = true
                legend.verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
                legend.horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
                legend.orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
                legend.setDrawInside(false)
                legend.textSize = 12f
                setEntryLabelTextSize(12f)
                setEntryLabelColor(Color.Black.value.toInt())
                animateY(1000)
            }
        },
        update = { chart ->
            val dataSet = PieDataSet(data, "").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                valueTextSize = 12f
                valueFormatter = PercentFormatter(chart)
                valueTextColor = Color.Black.value.toInt()
            }
            chart.data = PieData(dataSet)
            chart.invalidate()
        }
    )
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("tr", "TR")).format(amount)
} 