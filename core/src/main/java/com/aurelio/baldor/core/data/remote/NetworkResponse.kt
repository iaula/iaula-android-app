package com.aurelio.baldor.core.data.remote

data class NetworkResponse<T>(
    val success: Boolean,
    val message: String,
    val detail: T?,
    val error: String?
)
