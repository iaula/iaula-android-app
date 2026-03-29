package com.aurelio.baldor.core.data.remote

import com.aurelio.baldor.core.data.local.AuthPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(
    private val prefs: AuthPreferences,
    private val api: AuthApiService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // No agregar token en /auth/login
        if (!request.url.encodedPath.contains("/auth/login")) {
            val token = runBlocking { prefs.authToken.firstOrNull() }
            token?.let {
                val newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
                return handleResponse(chain, newRequest)
            }
        }

        return handleResponse(chain, request)
    }

    private fun handleResponse(chain: Interceptor.Chain, request: okhttp3.Request): Response {
        try {
            val response = chain.proceed(request)

            if (request.url.encodedPath.contains("/auth/login")) {
                return response
            }

            if (response.code == 401) {
                val refreshResponse = try {
                    runBlocking { api.refresh() }
                } catch (e: Exception) {
                    null
                }

                if (refreshResponse != null && refreshResponse.success && refreshResponse.detail != null) {
                    val newToken = refreshResponse.detail.access_token
                    runBlocking { prefs.saveTokens(newToken) }

                    val retryRequest = request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()

                    response.close()
                    return chain.proceed(retryRequest)
                }
            }

            return response
        } catch (e: IOException) {
            if (e is NoInternetException) throw e
            throw NoInternetException("Sin conexión a internet")
        } catch (e: Exception) {
            throw IOException(e.message ?: "Error desconocido en la red", e)
        }
    }
}

class NoInternetException(message: String): IOException(message)
