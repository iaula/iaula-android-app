package com.aurelio.baldor.core.data.repository

import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.middleware.safeApiCall
import com.aurelio.baldor.core.data.remote.FamiliaApiService
import com.aurelio.baldor.core.data.remote.FamiliaResponse

class FamiliaRepository(
    private val api: FamiliaApiService,
) {
    suspend fun getFamilias(codigo: String): NetworkResult<List<FamiliaResponse>>{
        val result = safeApiCall { api.getFamilias(codigo = codigo) }
        return when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet

        }
    }
}