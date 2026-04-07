package com.aurelio.baldor.core.data.remote

import com.aurelio.baldor.core.data.middleware.NetworkResponse
import com.aurelio.baldor.core.domain.model.UserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val usuario: String, val password: String)
data class RefreshRequest(val refresh_token: String?)
data class AuthResponse(val token_type: String, val refresh_token: String, val access_token: String, val user_data: UserData)
data class InstitutionResponse(val codigo: String, val nombre: String, val api_url: String, val api_prefix: String, val logo_url: String)

interface AuthApiService {

    @GET("institucion")
    suspend fun getInstitutions(): NetworkResponse<List<InstitutionResponse>>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<AuthResponse>

    @GET("auth/profile")
    suspend fun getProfile(): NetworkResponse<UserData>

    @POST("auth/refresh")
    suspend fun refresh(@Body request: RefreshRequest): NetworkResponse<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(): NetworkResponse<String>
}
