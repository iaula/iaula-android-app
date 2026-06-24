package com.aurelio.baldor.core.components.Shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aurelio.baldor.core.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBottomBar(
    navController: NavController,
    currentRoute: String,
    isLandscape: Boolean = false
) {

    val viewModel: NavigationBottomBarViewModel = koinViewModel()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val items = listOf(
        Triple("Inicio", ImageVector.vectorResource(R.drawable.ic_home), "home"),
        Triple("Notas", ImageVector.vectorResource(R.drawable.ic_notes), "notas"),
        Triple("Agenda", ImageVector.vectorResource(R.drawable.ic_calendar), "agenda"),
        Triple("Menú", Icons.Default.Menu, "menu")
    )

    if (isLandscape) {
        NavigationRail(
            containerColor = White,
            header = {
                Spacer(modifier = Modifier.height(16.dp))
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            items.forEach { (label, icon, route) ->
                val isSelected = currentRoute == route
                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        if (route == "menu") {
                            showBottomSheet = true
                        } else if (currentRoute != route) {
                            navController.navigate(route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) colorResource(R.color.secondary) else Color.Gray
                            )
                            Text(
                                text = label,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) colorResource(R.color.secondary) else Color.Gray
                                )
                            )
                        }
                    },
                    label = null,
                    alwaysShowLabel = false,
                    colors = NavigationRailItemDefaults.colors(indicatorColor = Color.Transparent),
                    modifier = Modifier.background(
                        if (isSelected) colorResource(R.color.third) else Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    } else {
        NavigationBar(
            containerColor = White,
            tonalElevation = 8.dp
        ) {
            items.forEach { (label, icon, route) ->
                val isSelected = currentRoute == route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (route == "menu") {
                            showBottomSheet = true
                        } else if (currentRoute != route) {
                            navController.navigate(route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) colorResource(R.color.secondary) else Color.Gray
                            )
                            Text(
                                text = label,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) colorResource(R.color.secondary) else Color.Gray
                                )
                            )
                        }
                    },
                    label = null,
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                    modifier = Modifier.background(
                        if (isSelected) colorResource(R.color.third) else Color.Transparent
                    )
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            "Cerrar sesión",
                            style = TextStyle(fontWeight = FontWeight.Normal),
                            color = colorResource(R.color.text_primary)
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = colorResource(R.color.secondary)
                        )
                    },
                    modifier = Modifier.clickable {
                        showBottomSheet = false
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
