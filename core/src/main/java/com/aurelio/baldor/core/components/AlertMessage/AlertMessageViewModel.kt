package com.aurelio.baldor.core.components.AlertMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlertMessageViewModel : ViewModel() {
    private val _alertState = MutableStateFlow<AlertState?>(null)
    val alertState: StateFlow<AlertState?> = _alertState

    fun showMessage(message: String, type: AlertType) {
        _alertState.value = AlertState(message = message, type = type)
        viewModelScope.launch {
            delay(2000) // 2 segundos
            dismiss()
        }
    }

    fun dismiss() {
        _alertState.value = null
    }
}