package com.aurelio.baldor.feature_login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.InstitutionResponse
import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.domain.usecase.InstitutionsUseCase
import com.aurelio.baldor.core.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val institutionUseCase: InstitutionsUseCase,
    private val authPreferences: AuthPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        loadInstitutions()
    }

    private fun loadInstitutions() {
        viewModelScope.launch {
            authPreferences.clear()
            _uiState.update { it.copy(isLoadingInstitutions = true) }
            when (val result = institutionUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            institutions = result.data,
                            isLoadingInstitutions = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingInstitutions = false
                        )
                    }
                }

                NetworkResult.NoInternet -> {
                    _uiState.update {
                        it.copy(
                            result = LoginResult.Error("Sin conexión a internet")
                        )
                    }
                }
            }
        }
    }

    fun onInstitutionSelected(institution: InstitutionResponse) {
        viewModelScope.launch {
            authPreferences.saveInstitutionConfig(
                institution.api_url,
                institution.api_prefix
            )
            _uiState.update { it.copy(selectedInstitution = institution) }
        }
    }

    fun onUsuarioChanged(newUsuario: String) {
        _uiState.update { it.copy(usuario = newUsuario) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun login() {
        val usuario = _uiState.value.usuario
        val password = _uiState.value.password

        viewModelScope.launch {
            _uiState.update { it.copy(result = LoginResult.Loading) }

            when (val result = loginUseCase(usuario, password)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            result = LoginResult.Success(result.data)
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            result = LoginResult.Error(result.message ?: "Error desconocido")
                        )
                    }
                }

                NetworkResult.NoInternet -> {
                    _uiState.update {
                        it.copy(
                            result = LoginResult.Error("Sin conexión a internet")
                        )
                    }
                }
            }
        }
    }
}
