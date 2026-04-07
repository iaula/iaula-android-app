package com.aurelio.baldor.feature_home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.domain.model.UserData
import com.aurelio.baldor.core.domain.usecase.AsistenciaUseCase
import com.aurelio.baldor.core.domain.usecase.FamiliasUseCase
import com.aurelio.baldor.feature_home.components.cards.ChildrenDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val familiasUseCase: FamiliasUseCase,
    private val asistenciaUseCase: AsistenciaUseCase,
    private val authPreferences: AuthPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            authPreferences.userData.collect { jsonUserData ->
                if (!jsonUserData.isNullOrBlank()) {
                    val userData = Gson().fromJson(jsonUserData, UserData::class.java)
                    _uiState.update { it.copy(userData = userData) }
                    if (userData.role == 4) {
                        loadHijos(userData.usuario)
                    }
                }
            }

        }
    }

    private suspend fun loadHijos(codigo: String) {
        when (val result = familiasUseCase(codigo = codigo)) {
            is NetworkResult.Success -> {
                val listaHijos = result.data.firstOrNull()?.hijos ?: emptyList()
                _uiState.update { it.copy(hijos = listaHijos) }
                listaHijos.firstOrNull()?.let { primerHijo ->
                    val dto = ChildrenDto(
                        codigo = primerHijo.id_estudiante,
                        name = primerHijo.nombres,
                        grade = primerHijo.grado_actual ?: "-",
                        gender = primerHijo.sexo,
                        id = primerHijo.id_persona
                    )
                    onChildSelected(dto)
                }
            }

            else -> {
                _uiState.update { it.copy(hijos = emptyList()) }
            }
        }
    }

    fun onChildSelected(child: ChildrenDto) {
        viewModelScope.launch {
            val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = formatDate.format(Date())
            when (val result =
                asistenciaUseCase(date_ini = today, date_fin = today, codigo = child.codigo)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(asistencia = result.data.firstOrNull()) }
                }

                else -> {
                    _uiState.update { it.copy(asistencia = null) }
                }
            }
        }
    }
}