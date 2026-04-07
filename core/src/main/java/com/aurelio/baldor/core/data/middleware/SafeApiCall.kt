package com.aurelio.baldor.core.data.middleware

import com.google.gson.Gson
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> NetworkResponse<T>
): NetworkResult<T> {
    return try {
        val response = apiCall()

        if (response.status) {
            NetworkResult.Success((response.detail ?: response.message) as T)
        } else {
            NetworkResult.Error(response.message ?: "Error en la operación")
        }
    } catch (e: NoInternetException) {
        NetworkResult.NoInternet
    } catch (e: CancellationException) {
        throw e
    } catch (e: HttpException) {
        val errorMsg = parseHttpError(e)
        NetworkResult.Error(errorMsg)
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Error desconocido")
    }
}

private fun parseHttpError(e: HttpException): String {
    return try {
        val errorBody = e.response()?.errorBody()?.string()
        val parsed = Gson().fromJson(errorBody, NetworkResponse::class.java)
        parsed?.error ?: parsed?.message ?: "Error de servidor"
    } catch (_: Exception) {
        "Error de red inesperado"
    }
}