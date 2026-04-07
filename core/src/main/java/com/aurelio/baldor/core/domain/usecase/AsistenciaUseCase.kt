package com.aurelio.baldor.core.domain.usecase

import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.remote.AsistenciaResponse
import com.aurelio.baldor.core.data.repository.AsistenciaRepository

class AsistenciaUseCase(private val repository: AsistenciaRepository) {
    suspend operator fun invoke(
        date_ini: String,
        date_fin: String,
        codigo: String
    ): NetworkResult<List<AsistenciaResponse>> {
        return repository.getAsistenciaEstudiante(
            date_ini = date_ini,
            date_fin = date_fin,
            codigo = codigo
        )
    }
}