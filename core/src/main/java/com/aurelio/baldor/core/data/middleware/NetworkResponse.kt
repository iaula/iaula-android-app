package com.aurelio.baldor.core.data.middleware

data class NetworkResponse<T>(
    val status: Boolean,
    val message: String,
    val detail: T?,
    val error: String?
)
