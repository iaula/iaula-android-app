package com.aurelio.baldor.core.data.repository

import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.AuthApiService
import com.aurelio.baldor.core.data.remote.AuthResponse
import com.aurelio.baldor.core.data.remote.InstitutionResponse
import com.aurelio.baldor.core.data.remote.LoginRequest
import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.middleware.safeApiCall
import com.aurelio.baldor.core.domain.model.UserData
import com.google.gson.Gson

class AuthRepository(
    private val api: AuthApiService,
    private val prefs: AuthPreferences
) {

    suspend fun getInstitutions(): NetworkResult<List<InstitutionResponse>> {
        val result = safeApiCall { api.getInstitutions() }
        return when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet
        }
    }

    suspend fun login(usuario: String, password: String): NetworkResult<AuthResponse> {
        val result = safeApiCall { api.login(LoginRequest(usuario, password)) }
        return when (result) {
            is NetworkResult.Success -> {
                prefs.saveTokens(result.data.access_token)
                prefs.saveRefreshToken(result.data.refresh_token)
                prefs.saveUserData(Gson().toJson(result.data.user_data))
                NetworkResult.Success(result.data)
            }

            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet
        }

    }

    suspend fun getProfile(): NetworkResult<UserData> =
        safeApiCall { api.getProfile() }


    suspend fun logout(): NetworkResult<String> {
        val result = safeApiCall { api.logout() }
        return when (result) {
            is NetworkResult.Success -> {
                prefs.clear()
                NetworkResult.Success(result.data)
            }

            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet
        }
    }
}
