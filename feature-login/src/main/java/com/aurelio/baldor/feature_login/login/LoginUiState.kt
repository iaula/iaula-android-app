package com.aurelio.baldor.feature_login.login

import com.aurelio.baldor.core.data.remote.AuthResponse
import com.aurelio.baldor.core.data.remote.InstitutionResponse

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    data class Success(val data: AuthResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

data class LoginUiState(
    val usuario: String = "",
    val password: String = "",
    val result: LoginResult = LoginResult.Idle,

    val institutions: List<InstitutionResponse> = emptyList(),
    val isLoadingInstitutions: Boolean = false,
    val selectedInstitution: InstitutionResponse? = null,
)
