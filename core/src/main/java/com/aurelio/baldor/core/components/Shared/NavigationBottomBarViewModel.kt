package com.aurelio.baldor.core.components.Shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelio.baldor.core.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class NavigationBottomBarViewModel(
private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}