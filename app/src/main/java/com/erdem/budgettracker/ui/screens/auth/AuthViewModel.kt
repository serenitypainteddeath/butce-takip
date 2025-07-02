package com.erdem.budgettracker.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdem.budgettracker.auth.AuthManager
import com.erdem.budgettracker.data.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    val authState: StateFlow<AuthState> = authManager.authState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authManager.signIn(email, password)
        }
    }

    fun signUp(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            authManager.signUp(email, password, displayName)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authManager.signOut()
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            authManager.resetPassword(email)
        }
    }

    fun clearError() {
        authManager.clearError()
    }
} 