package com.aurelio.baldor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aurelio.baldor.app.ui.theme.AurelioBaldorAppTheme
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.feature_home.ui.HomeScreen
import com.aurelio.baldor.feature_login.login.LoginScreen

class MainActivity : ComponentActivity() {
    private lateinit var authPreferences: AuthPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authPreferences = AuthPreferences(this)
        enableEdgeToEdge()
        setContent {
            AurelioBaldorAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen (navController = navController)
        }
        composable("login") {
            LoginScreen(
                navController = navController
            )
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("notas") { /* Pantalla de Notas */ }
        composable("agenda") { /* Pantalla de Agenda */ }
        composable("menu") { /* Pantalla de Menú */ }
    }
}
