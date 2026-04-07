package com.aurelio.baldor.feature_home.home

import com.aurelio.baldor.core.data.remote.AsistenciaResponse
import com.aurelio.baldor.core.data.remote.FamiliaHijoResponse
import com.aurelio.baldor.core.domain.model.UserData

data class HomeUiState(
    val userData : UserData? = null,
    val hijos: List<FamiliaHijoResponse> = emptyList(),
    val asistencia: AsistenciaResponse? = null
)