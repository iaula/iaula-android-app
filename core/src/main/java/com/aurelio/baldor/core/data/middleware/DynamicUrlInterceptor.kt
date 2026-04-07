package com.aurelio.baldor.core.data.middleware

import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.di.NetworkConfig
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class DynamicUrlInterceptor(
    private val prefs: AuthPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val path = originalRequest.url.encodedPath
        val builder = originalRequest.newBuilder()

        val dynamicUrl = runBlocking { prefs.apiUrl.firstOrNull() }
        val dynamicPrefix = runBlocking { prefs.apiPrefix.firstOrNull() }

        if (path.contains("/institucion")) {
            builder.addHeader("X-App-Key", NetworkConfig.API_KEY)
            builder.url("${NetworkConfig.BASE_URL}api/v2/institucion".toHttpUrl())
        } else if (!dynamicUrl.isNullOrBlank()) {
            val base = if (dynamicUrl.endsWith("/")) dynamicUrl else "$dynamicUrl/"
            val prefix = dynamicPrefix?.trim('/') ?: ""
            val cleanPath = path.removePrefix("/")

            val newUrlString = "$base$prefix/$cleanPath".replace(Regex("(?<!:)/+"), "/")
            newUrlString.toHttpUrlOrNull()?.let { newBaseHttpUrl ->
                val finalUrl = newBaseHttpUrl.newBuilder()
                    .encodedQuery(originalUrl.encodedQuery)
                    .build()
                builder.url(finalUrl)
            }
        }

        return chain.proceed(builder.build())
    }
}