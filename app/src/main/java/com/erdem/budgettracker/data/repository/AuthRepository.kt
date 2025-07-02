package com.erdem.budgettracker.data.repository

import com.erdem.budgettracker.data.local.UserDao
import com.erdem.budgettracker.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
) {
    fun getCurrentUser(): Flow<User?> = userDao.getCurrentUser()

    suspend fun getCurrentUserOnce(): User? = userDao.getCurrentUserOnce()

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = firebaseUser.toUser()
                userDao.logoutAllUsers()
                userDao.insertUser(user.copy(isLoggedIn = true))
                Result.success(user)
            } else {
                Result.failure(Exception("Giriş başarısız"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(translateFirebaseError(e)))
        }
    }

    suspend fun createUserWithEmailAndPassword(
        email: String, 
        password: String, 
        displayName: String
    ): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                // Kullanıcı adını güncelle
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                firebaseUser.updateProfile(profileUpdates).await()

                val user = firebaseUser.toUser().copy(displayName = displayName)
                userDao.logoutAllUsers()
                userDao.insertUser(user.copy(isLoggedIn = true))
                Result.success(user)
            } else {
                Result.failure(Exception("Kayıt başarısız"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(translateFirebaseError(e)))
        }
    }

    suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            userDao.logoutAllUsers()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception(translateFirebaseError(e)))
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception(translateFirebaseError(e)))
        }
    }

    private fun translateFirebaseError(exception: Exception): String {
        return when {
            exception is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Geçersiz email adresi"
                    "ERROR_WRONG_PASSWORD" -> "Yanlış şifre"
                    "ERROR_USER_NOT_FOUND" -> "Bu email adresi ile kayıtlı kullanıcı bulunamadı"
                    "ERROR_USER_DISABLED" -> "Bu hesap devre dışı bırakılmış"
                    "ERROR_TOO_MANY_REQUESTS" -> "Çok fazla deneme yapıldı. Lütfen daha sonra tekrar deneyin"
                    "ERROR_OPERATION_NOT_ALLOWED" -> "Bu işlem izin verilmiyor"
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "Bu email adresi zaten kullanımda"
                    "ERROR_WEAK_PASSWORD" -> "Şifre çok zayıf. En az 6 karakter olmalı"
                    "ERROR_NETWORK_REQUEST_FAILED" -> "İnternet bağlantısı hatası"
                    else -> exception.message ?: "Bilinmeyen hata oluştu"
                }
            }
            exception.message?.contains("password") == true -> "Şifre hatalı"
            exception.message?.contains("email") == true -> "Email adresi hatalı"
            exception.message?.contains("network") == true -> "İnternet bağlantısı hatası"
            else -> exception.message ?: "Bilinmeyen hata oluştu"
        }
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            uid = uid,
            email = email ?: "",
            displayName = displayName ?: "",
            photoUrl = null // TODO: Firebase User photoUrl property'si güncellenecek
        )
    }
}
