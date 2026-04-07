package com.aurelio.baldor.core.domain.usecase

import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): NetworkResult<String> {
        return repository.logout()
    }
}