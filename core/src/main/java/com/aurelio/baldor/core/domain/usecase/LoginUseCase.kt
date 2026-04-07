package com.aurelio.baldor.core.domain.usecase

import com.aurelio.baldor.core.data.remote.AuthResponse
import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): NetworkResult<AuthResponse> {
        return repository.login(username, password)
    }
}