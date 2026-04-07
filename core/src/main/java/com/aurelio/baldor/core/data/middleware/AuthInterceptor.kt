package com.aurelio.baldor.core.data.middleware

import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.AuthApiService
import com.aurelio.baldor.core.data.remote.RefreshRequest
import com.aurelio.baldor.core.di.NetworkConfig.API_KEY
import com.aurelio.baldor.core.di.NetworkConfig.BASE_URL
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(
    private val prefs: AuthPreferences,
    private val api: AuthApiService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath
        var request = originalRequest.newBuilder()

        if (!path.contains("/auth/login") && !path.contains("/auth/refresh") && !path.contains("/institucion")) {
            runBlocking { prefs.authToken.firstOrNull() }?.let {
                request.addHeader("Authorization", "Bearer $it")
            }
        }

        return handleResponse(chain, request.build())
    }

    private fun handleResponse(chain: Interceptor.Chain, request: Request): Response {
        try {
            val response = chain.proceed(request)

            if (response.code == 401 && !request.url.encodedPath.contains("/auth/login")) {

                val refreshResponse = try {
                    runBlocking { api.refresh(RefreshRequest(prefs.refreshToken.firstOrNull())) }
                } catch (e: Exception) {
                    null
                }

                if (refreshResponse != null && refreshResponse.status && refreshResponse.detail != null) {
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

class NoInternetException(message: String) : IOException(message)
