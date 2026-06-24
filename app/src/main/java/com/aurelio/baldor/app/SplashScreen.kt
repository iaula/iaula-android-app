package com.aurelio.baldor.app

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.requestAppUpdateInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    navController: NavController,
    authPreferences: AuthPreferences = koinInject()
) {
    val context = LocalContext.current
    var showUpdateDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

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
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }

    if (showUpdateDialog) {
        AlertDialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = { Text("Actualización Disponible") },
            text = { Text("Para seguir usando la aplicación, es necesario instalar la última versión disponible en Play Store.") },
            confirmButton = {
                Button(onClick = {
                    val packageName = context.packageName
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: Exception) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }) {
                    Text("Ir a Play Store")
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        val appUpdateManager = AppUpdateManagerFactory.create(context)

        try {
            // Consultar a Google Play si hay actualización
            val appUpdateInfo = appUpdateManager.requestAppUpdateInfo()
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                showUpdateDialog = true
                isLoading = false
                return@LaunchedEffect // Detener flujo para que no navegue al login/home
            }
        } catch (e: Exception) {
            // Si falla (ej: no hay internet), ignoramos y seguimos el flujo normal
        }

        // Si no hay actualización o no hay internet, continuamos el flujo normal
        val token = authPreferences.authToken.first()
        delay(1500)
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
