package com.aurelio.baldor.core.data.repository

import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.AuthApiService
import com.aurelio.baldor.core.data.remote.AuthResponse
import com.aurelio.baldor.core.data.remote.LoginRequest
import com.aurelio.baldor.core.data.remote.NetworkResult
import com.aurelio.baldor.core.data.remote.safeApiCall
import com.aurelio.baldor.core.domain.model.UserProfile

class AuthRepository(
    private val api: AuthApiService,
    private val prefs: AuthPreferences
) {

    suspend fun login(usuario: String, password: String): NetworkResult<AuthResponse> {
        val result = safeApiCall { api.login(LoginRequest(usuario, password)) }
        return when (result) {
            is NetworkResult.Success -> {
                prefs.saveTokens(result.data.access_token)
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet
        }

    }

    suspend fun getProfile(): NetworkResult<UserProfile> =
        safeApiCall { api.getProfile() }


    suspend fun refreshToken(): NetworkResult<AuthResponse> {
        val result = safeApiCall { api.refresh() }
        return when (result) {
            is NetworkResult.Success -> {
                prefs.saveTokens(result.data.access_token)
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> result
            NetworkResult.NoInternet -> NetworkResult.NoInternet
        }
    }


    suspend fun logout() {
        safeApiCall { api.logout() }.also {
            if (it is NetworkResult.Success) prefs.clear()
        }
    }

    fun getTokenFlow() = prefs.authToken
}
