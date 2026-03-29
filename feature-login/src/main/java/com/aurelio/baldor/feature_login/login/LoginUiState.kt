package com.aurelio.baldor.feature_login.login

import com.aurelio.baldor.core.data.remote.AuthResponse

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    data class Success(val data: AuthResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

data class LoginUiState(
    val usuario: String = "",
    val password: String = "",
    val result: LoginResult = LoginResult.Idle
)
