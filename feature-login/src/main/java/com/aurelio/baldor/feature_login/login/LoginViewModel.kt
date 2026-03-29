package com.aurelio.baldor.feature_login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelio.baldor.core.data.remote.NetworkResult
import com.aurelio.baldor.core.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsuarioChanged(newUsuario: String) {
        _uiState.value = _uiState.value.copy(usuario = newUsuario)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        val usuario = _uiState.value.usuario
        val password = _uiState.value.password

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(result = LoginResult.Loading)

            when (val result = loginUseCase(usuario, password)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        result = LoginResult.Success(result.data)
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        result = LoginResult.Error(result.message ?: "Error desconocido")
                    )
                }
                NetworkResult.NoInternet -> {
                    _uiState.value = _uiState.value.copy(
                        result = LoginResult.Error("Sin conexión a internet")
                    )
                }
            }
        }
    }
}
