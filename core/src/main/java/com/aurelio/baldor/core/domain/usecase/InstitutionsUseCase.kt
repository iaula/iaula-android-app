package com.aurelio.baldor.core.domain.usecase

import com.aurelio.baldor.core.data.repository.AuthRepository

class InstitutionsUseCase (private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.getInstitutions()
}