package com.aurelio.baldor.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aurelio.baldor.core.data.local.AuthPreferences
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    authPreferences: AuthPreferences = AuthPreferences(LocalContext.current)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(com.aurelio.baldor.core.R.color.secondary)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(com.aurelio.baldor.core.R.drawable.logo_complete),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
        }
    }

    // Simula loading
    LaunchedEffect(Unit) {
        authPreferences.authToken.collect { token ->
            delay(1500) // si quieres mantener la animación de carga
            if (token.isNullOrEmpty()) {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }
}