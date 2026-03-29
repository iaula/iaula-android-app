package com.aurelio.baldor.core.data.remote

import com.aurelio.baldor.core.domain.model.UserProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val usuario: String, val password: String)
data class AuthResponse(val token_type: String, val expires_in: Long, val access_token: String)

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<AuthResponse>

    @GET("auth/profile")
    suspend fun getProfile(): NetworkResponse<UserProfile>

    @GET("auth/refresh")
    suspend fun refresh(): NetworkResponse<AuthResponse>

    @GET("auth/logout")
    suspend fun logout(): NetworkResponse<Unit>
}
