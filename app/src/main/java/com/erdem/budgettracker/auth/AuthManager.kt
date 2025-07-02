package com.erdem.budgettracker.auth

import com.erdem.budgettracker.data.model.AuthState
import com.erdem.budgettracker.data.model.User
import com.erdem.budgettracker.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val authRepository: AuthRepository
) {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val isAuthenticated: Flow<Boolean> = authRepository.getCurrentUser()
        .map { it != null }

    val currentUser: Flow<User?> = authRepository.getCurrentUser()

    suspend fun signIn(email: String, password: String) {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        
        authRepository.signInWithEmailAndPassword(email, password)
            .onSuccess { user ->
                _authState.value = AuthState(
                    isAuthenticated = true,
                    user = user,
                    isLoading = false,
                    error = null
                )
            }
            .onFailure { exception ->
                _authState.value = AuthState(
                    isAuthenticated = false,
                    user = null,
                    isLoading = false,
                    error = exception.message ?: "Bilinmeyen hata"
                )
            }
    }

    suspend fun signUp(email: String, password: String, displayName: String) {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        
        authRepository.createUserWithEmailAndPassword(email, password, displayName)
            .onSuccess { user ->
                _authState.value = AuthState(
                    isAuthenticated = true,
                    user = user,
                    isLoading = false,
                    error = null
                )
            }
            .onFailure { exception ->
                _authState.value = AuthState(
                    isAuthenticated = false,
                    user = null,
                    isLoading = false,
                    error = exception.message ?: "Bilinmeyen hata"
                )
            }
    }

    suspend fun signOut() {
        _authState.value = _authState.value.copy(isLoading = true)
        
        authRepository.signOut()
            .onSuccess {
                _authState.value = AuthState(
                    isAuthenticated = false,
                    user = null,
                    isLoading = false,
                    error = null
                )
            }
            .onFailure { exception ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Çıkış yaparken hata oluştu"
                )
            }
    }

    suspend fun resetPassword(email: String) {
        _authState.value = _authState.value.copy(isLoading = true, error = null)
        
        authRepository.resetPassword(email)
            .onSuccess {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = null
                )
            }
            .onFailure { exception ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Şifre sıfırlama başarısız"
                )
            }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
} 