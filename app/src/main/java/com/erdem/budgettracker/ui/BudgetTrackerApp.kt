package com.erdem.budgettracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.erdem.budgettracker.auth.AuthManager
import com.erdem.budgettracker.ui.screens.auth.AuthViewModel
import com.erdem.budgettracker.ui.screens.auth.LoginScreen
import com.erdem.budgettracker.ui.screens.auth.RegisterScreen
import com.erdem.budgettracker.ui.screens.home.HomeScreen
import com.erdem.budgettracker.ui.screens.stats.StatsScreen
import com.erdem.budgettracker.ui.screens.settings.SettingsScreen
import com.erdem.budgettracker.ui.screens.add_transaction.AddTransactionScreen
import com.erdem.budgettracker.ui.screens.exchange_rates.ExchangeRatesScreen

sealed class Screen(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Login : Screen("login", { Icon(Icons.Default.Login, contentDescription = null) }, "Giriş")
    object Register : Screen("register", { Icon(Icons.Default.PersonAdd, contentDescription = null) }, "Kayıt")
    object Home : Screen("home", { Icon(Icons.Default.Home, contentDescription = null) }, "Ana Sayfa")
    object Stats : Screen("stats", { Icon(Icons.Default.PieChart, contentDescription = null) }, "İstatistikler")
    object ExchangeRates : Screen("exchange_rates", { Icon(Icons.Default.CurrencyExchange, contentDescription = null) }, "Döviz Kurları")
    object Profile : Screen("profile", { Icon(Icons.Default.Person, contentDescription = null) }, "Profil")
    object AddTransaction : Screen("add_transaction", { Icon(Icons.Default.Add, contentDescription = null) }, "İşlem Ekle")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetTrackerApp(
    authManager: AuthManager? = null // Şimdilik kullanmıyoruz, daha sonra implement edeceğiz
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = authenticatedScreens.find { it.route == currentDestination?.route } ?: Screen.Home
    
    // AuthViewModel'den authentication state'i al
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    
    // Authentication durumuna göre ekranları belirle
    if (authState.isAuthenticated) {
        // Authenticated user interface
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.label) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    authenticatedScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = screen.icon,
                            label = { Text(screen.label) },
                            selected = currentScreen == screen,
                            onClick = {
                                if (currentScreen != screen) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (currentScreen != Screen.AddTransaction) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.AddTransaction.route)
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "İşlem Ekle")
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        onNavigateToAddTransaction = {
                            navController.navigate(Screen.AddTransaction.route)
                        }
                    )
                }
                composable(Screen.Stats.route) {
                    StatsScreen()
                }
                composable(Screen.ExchangeRates.route) {
                    ExchangeRatesScreen()
                }
                composable(Screen.Profile.route) {
                    SettingsScreen()
                }
                composable(Screen.AddTransaction.route) {
                    AddTransactionScreen(
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    } else {
        // Unauthenticated user interface - Login/Register screens
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                    onLoginSuccess = {
                        // Login başarılı olunca ana ekrana yönlendir
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterSuccess = {
                        // Kayıt başarılı olunca ana ekrana yönlendir
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

private val authenticatedScreens = listOf(
    Screen.Home,
    Screen.Stats,
    Screen.ExchangeRates,
    Screen.Profile
) 