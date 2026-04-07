package com.aurelio.baldor.core.domain.usecase

import com.aurelio.baldor.core.data.middleware.NetworkResult
import com.aurelio.baldor.core.data.remote.FamiliaResponse
import com.aurelio.baldor.core.data.repository.FamiliaRepository

class FamiliasUseCase (private val repository: FamiliaRepository) {
    suspend operator fun invoke(codigo: String): NetworkResult<List<FamiliaResponse>> {
        return repository.getFamilias(codigo)
    }
}