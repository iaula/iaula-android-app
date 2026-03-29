package com.aurelio.baldor.feature_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aurelio.baldor.core.components.Shared.NavigationBottomBar
import com.aurelio.baldor.feature_home.Comunicado
import kotlin.math.round

@Composable
fun HomeScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            Column {
                // Top Bar Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(com.aurelio.baldor.core.R.color.secondary))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(com.aurelio.baldor.core.R.drawable.logo_complete),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )

                    Box {
                        Surface(
                            modifier = Modifier.size(20.dp),
                            shape = CircleShape,
                            color = colorResource(com.aurelio.baldor.core.R.color.fourth)
                        ) {}
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificaciones",
                            tint = White,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(2.dp),
                        )
                        Surface(
                            color = Color.Red,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(10.dp)
                                .padding(2.dp)
                                .align(Alignment.TopEnd)
                                .border(1.dp, White, CircleShape)
                        ) {}
                    }
                }

                // Welcome section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(com.aurelio.baldor.core.R.color.secondary))
                        .padding(start = 20.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        color = colorResource(com.aurelio.baldor.core.R.color.third)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "FP",
                                color = colorResource(com.aurelio.baldor.core.R.color.secondary),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "¡Hola,\n Familia Perez!",
                        color = White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 32.sp
                    )
                }
            }
        },
        bottomBar = { NavigationBottomBar(navController, currentRoute) }
    ) { innerPadding ->
        HomeCompleteScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        )
    }
}

data class Children(val name: String, val grade: String, val gender: String, val selected: Boolean)

@Composable
fun HomeCompleteScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 👨‍👩‍👧‍👦 Sección hijos con scroll horizontal
        Text(
            "Seleccionar hijo(a)",
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(
                com.aurelio.baldor.core.R.color.text_primary
            ), fontWeight = FontWeight.Normal
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                listOf(
                    Children("Mateo", "5to Sec", "M", true),
                    Children("Sofía", "2do Sec", "F", false),
                    Children("Emma", "5to Sec", "F", false)
                )
            ) { child ->
                Card(
                    modifier = Modifier
                        .width(102.dp)
                        .height(97.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(if (child.gender == "M") com.aurelio.baldor.core.R.drawable.profile_male else com.aurelio.baldor.core.R.drawable.profile_female),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)                       // clip to the circle shape
                                .border(
                                    width = if(child.selected) 2.dp else 0.dp,
                                    color = if(child.selected) colorResource(com.aurelio.baldor.core.R.color.secondary) else Color.White,
                                    shape = CircleShape
                                ).padding(5.dp)
                        )
                        Text(
                            child.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(if(child.selected) com.aurelio.baldor.core.R.color.text_primary else com.aurelio.baldor.core.R.color.text_secondary),
                            fontWeight = if(child.selected) FontWeight.Bold else FontWeight.Normal
                        )
                        Text(
                            child.grade,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(if(child.selected) com.aurelio.baldor.core.R.color.text_primary else com.aurelio.baldor.core.R.color.text_secondary)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🕒 Sección Asistencia
        Text(
            "Asistencia", style = MaterialTheme.typography.titleMedium, color = colorResource(
                com.aurelio.baldor.core.R.color.secondary
            ), fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(com.aurelio.baldor.core.R.color.warning_third))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                // Fila Superior: Fecha y Etiqueta
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            tint = colorResource(com.aurelio.baldor.core.R.color.text_primary),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Lunes, 16 Febrero 2026",
                            fontSize = 14.sp,
                            color = colorResource(com.aurelio.baldor.core.R.color.text_primary)
                        )
                    }

                    Surface(
                        color = colorResource(com.aurelio.baldor.core.R.color.warning_secondary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Tardanza",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = colorResource(com.aurelio.baldor.core.R.color.warning_primary),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fila Central: Ingreso y Salida
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Columna Ingreso
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                tint = colorResource(com.aurelio.baldor.core.R.color.text_third),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Ingreso: 07:00AM", color = colorResource(com.aurelio.baldor.core.R.color.text_third), fontSize = 12.sp)
                        }
                        Text(
                            text = "07:40AM",
                            color = colorResource(com.aurelio.baldor.core.R.color.warning_fourth),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 26.dp)
                        )
                    }

                    // Columna Salida
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                tint = colorResource(com.aurelio.baldor.core.R.color.text_third),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Salida: 03:00PM", color = colorResource(com.aurelio.baldor.core.R.color.text_third), fontSize = 12.sp)
                        }
                        Text(
                            text = "-",
                            color = colorResource(com.aurelio.baldor.core.R.color.text_third),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fila Inferior: Link de Justificación
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = null,
                        tint = colorResource(com.aurelio.baldor.core.R.color.warning_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Justificar tardanza",
                        color = colorResource(com.aurelio.baldor.core.R.color.warning_primary),
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📊 Sección botones (Notas, Incidencias, Asistencia) con scroll horizontal
        InteractiveMenuCarousel()

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Sección Comunicados con scroll horizontal
        SeccionComunicados(listOf(
            Comunicado("Reunión de padres", Icons.Outlined.PlayCircle),
            Comunicado("Libretas 2do bimestre", Icons.Outlined.Description),
            Comunicado("Cierre de bimestre", Icons.Outlined.Star)
        ))
    }
}


@Composable
fun ComunicadosItem(texto: String) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(texto, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// --- Modelo de Datos ---
data class MenuOption(
    val title: String,
    val icon: ImageVector
)

// --- Datos de Ejemplo ---
val menuOptionsList = listOf(
    MenuOption("Notas", Icons.Outlined.AssignmentTurnedIn),
    MenuOption("Incidencias", Icons.Outlined.WarningAmber),
    MenuOption("Asistencia", Icons.Outlined.CalendarMonth),
    MenuOption("Más Opciones", Icons.Outlined.AssignmentTurnedIn), // Ejemplo de item extra
)

// --- Colores ---
val ColorLightRedBackground = Color(0xFFFEE2E2)
val ColorIconRed = Color(0xFFDC2626)
val ColorTextDark = Color(0xFF374151)
val ColorUnselectedIndicator = Color(0xFFFECACA)

// --- Componente Item Individual ---
@Composable
fun MenuCard(option: MenuOption) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(160.dp) // Ancho fijo para cada tarjeta
            .padding(vertical = 8.dp) // Pequeño padding vertical para la sombra
            .aspectRatio(1f), // Hacerlas cuadradas
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Círculo de fondo del icono
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(ColorLightRedBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = option.title,
                    tint = ColorIconRed,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = option.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextDark
            )
        }
    }
}

// --- Componente de Indicadores ---
@Composable
fun PagerIndicators(pagerState: androidx.compose.foundation.pager.PagerState, pageCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            // Usar deriveStateOf para optimizar recomposiciones
            val isSelected by remember(pagerState) {
                derivedStateOf { pagerState.currentPage == index }
            }

            val color = if (isSelected) ColorIconRed else ColorUnselectedIndicator
            val width = if (isSelected) 32.dp else 16.dp // Más ancho si está seleccionado

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(width)
                    .height(8.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

// --- Componente Principal ---
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveMenuCarousel() {
    val pageCount = menuOptionsList.size

    // Para que los indicadores se sincronicen, usamos HorizontalPager en lugar de LazyRow puro.
    // HorizontalPager es la forma moderna de Material 3 para carruseles por páginas.
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(bottom = 16.dp)
    ) {
        // Carrusel Horizontal
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp), // Espacio en los bordes para ver las tarjetas vecinas
            pageSpacing = 16.dp, // Espacio entre tarjetas
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            MenuCard(option = menuOptionsList[page])
        }

        // Indicadores inferiores
        PagerIndicators(pagerState = pagerState, pageCount = pageCount)
    }
}

data class Comunicado(
    val titulo: String,
    val icon: ImageVector,
    val color: Color = Color(0xFFB00020) // El rojo oscuro de la imagen
)

@Composable
fun ComunicadoCard(comunicado: Comunicado) {
    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 180.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)), // Gris muy claro
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = comunicado.icon,
                contentDescription = null,
                tint = comunicado.color,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = comunicado.titulo,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SeccionComunicados(lista: List<Comunicado>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Comunicados",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFB00020)
                )
            )
            Text(
                text = "Ver todo",
                style = TextStyle(
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Gray
                ),
                modifier = Modifier.clickable() { /* Acción */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista Horizontal
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lista) { item ->
                ComunicadoCard(item)
            }
        }
    }
}