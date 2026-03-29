package com.aurelio.baldor.core.components.AlertMessage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info

enum class AlertType { SUCCESS, ERROR, WARNING, CONFIRMATION }

data class AlertState(
    val message: String = "",
    val type: AlertType = AlertType.SUCCESS
)

@Composable
fun AlertMessageUi(
    state: AlertState
) {

    val (icon, bgColor) = when (state.type) {
        AlertType.SUCCESS -> Icons.Filled.CheckCircle to Color(0xFF4CAF50)
        AlertType.ERROR -> Icons.Filled.Error to Color(0xFFF44336)
        AlertType.WARNING -> Icons.Filled.Warning to Color(0xFFFFC107)
        AlertType.CONFIRMATION -> Icons.Filled.Info to Color(0xFF2196F3)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            containerColor = bgColor
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = state.message, color = Color.White)
            }
        }
    }
}