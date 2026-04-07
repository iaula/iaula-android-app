package com.aurelio.baldor.core.data.repository

import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.middleware.safeApiCall
import com.aurelio.baldor.core.data.remote.AsistenciaApiService
import com.aurelio.baldor.core.data.remote.AsistenciaResponse

class AsistenciaRepository(
    private val api: AsistenciaApiService,
) {
    suspend fun getAsistenciaEstudiante(
        date_ini: String,
        date_fin: String,
        codigo: String
    ): NetworkResult<List<AsistenciaResponse>> {
        println("DEBUG_API: date_ini=$date_ini&date_fin=$date_fin&codigo=$codigo")

        val result = safeApiCall {
            api.getAsistenciaEstudiante(
                date_ini = date_ini,
                date_fin = date_fin,
                codigo = codigo
            )
        }
        return when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data)
            }

            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet

        }
    }
}