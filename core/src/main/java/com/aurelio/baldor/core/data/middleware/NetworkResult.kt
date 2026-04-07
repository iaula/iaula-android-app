package com.aurelio.baldor.core.data.middleware

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error(val message: String): NetworkResult<Nothing>()
    object NoInternet: NetworkResult<Nothing>()
}
