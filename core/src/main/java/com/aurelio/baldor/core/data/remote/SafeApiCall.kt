package com.aurelio.baldor.core.data.remote

import com.google.gson.Gson
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> NetworkResponse<T>
): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.success && response.detail != null) {
            NetworkResult.Success(response.detail)
        } else {
            NetworkResult.Error(response.message)
        }
    } catch (e: NoInternetException) {
        NetworkResult.NoInternet
    } catch (e: CancellationException) {
        throw e // importante para no atrapar cancelaciones de coroutines
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val parsed = try {
            Gson().fromJson(errorBody, NetworkResponse::class.java)
        } catch (_: Exception) {
            null
        }
        val errorMsg = parsed?.error ?: parsed?.message ?: "Error desconocido"
        NetworkResult.Error(errorMsg)
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Error desconocido")
    }
}
