package com.aurelio.baldor.feature_home.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aurelio.baldor.core.R
import com.aurelio.baldor.core.components.Shared.NavigationBottomBar
import com.aurelio.baldor.feature_home.components.cards.AttendanceDelay
import com.aurelio.baldor.feature_home.components.cards.AttendancePresent
import com.aurelio.baldor.feature_home.components.cards.ChildrenDto
import com.aurelio.baldor.feature_home.components.cards.ChildrenCard
import com.aurelio.baldor.feature_home.components.cards.CommunicationCard
import com.aurelio.baldor.feature_home.components.cards.CommunicationDto
import com.aurelio.baldor.feature_home.components.cards.MenuCard
import com.aurelio.baldor.feature_home.components.cards.MenuDto
import com.aurelio.baldor.feature_home.components.cards.NonAttendance

@Composable
fun HomeScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    val childrenList = remember {
        listOf(
            ChildrenDto("Mateo", "5to Sec", "M", 1),
            ChildrenDto("Sofía", "2do Sec", "F", 2),
            ChildrenDto("Emma", "5to Sec", "F", 3)
        )
    }

    var selectedChildId by remember { mutableStateOf(childrenList.first().id) }

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
                        .background(colorResource(R.color.secondary))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo_complete),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )

                    Box {
                        Surface(
                            modifier = Modifier.size(20.dp),
                            shape = CircleShape,
                            color = colorResource(R.color.fourth)
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
                        .background(colorResource(R.color.secondary))
                        .padding(start = 20.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        color = colorResource(R.color.third)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "FP",
                                color = colorResource(R.color.secondary),
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 👨‍👩‍👧‍👦 Sección hijos con scroll horizontal
            SectionChildren(children = childrenList, selectedId = selectedChildId, onChildSelected = { child -> selectedChildId = child.id})

            Spacer(modifier = Modifier.height(16.dp))

            // 🕒 Sección Asistencia
            SectionAttendace(selectedChildId)

            Spacer(modifier = Modifier.height(16.dp))

            // 📊 Sección botones (Notas, Incidencias, Asistencia) con scroll horizontal
            SectionMenu()

            Spacer(modifier = Modifier.height(16.dp))

            // 📌 Sección Comunicados con scroll horizontal
            SectionCommunication(
                listOf(
                    CommunicationDto(
                        "Reunión de padres",
                        ImageVector.vectorResource(R.drawable.ic_volume)
                    ),
                    CommunicationDto(
                        "Libretas 2do bimestre",
                        ImageVector.vectorResource(R.drawable.ic_document)
                    ),
                    CommunicationDto(
                        "Cierre de bimestre",
                        ImageVector.vectorResource(R.drawable.ic_calendar_star)
                    )
                )
            )
        }
    }
}

@Composable
fun SectionChildren(children: List<ChildrenDto>, selectedId: Int, onChildSelected: (ChildrenDto) -> Unit) {
    Text(
        "Seleccionar hijo(a)",
        style = MaterialTheme.typography.titleSmall,
        color = colorResource(
            R.color.text_primary
        ), fontWeight = FontWeight.Normal
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(children) { child ->
            val isSelected = child.id == selectedId
            Box(modifier = Modifier.clickable { onChildSelected(child) }) {
                ChildrenCard(child, isSelected)
            }
        }
    }
}

@Composable
fun SectionAttendace(childId: Int) {
    Text(
        "Asistencia", style = MaterialTheme.typography.titleMedium, color = colorResource(
            R.color.secondary
        ), fontWeight = FontWeight.Bold
    )
    when (childId) {
        1 -> AttendanceDelay()
        2 -> AttendancePresent()
        3 -> NonAttendance()
        else -> AttendancePresent() // Estado por defecto
    }
}

@Composable
fun SectionMenu() {
    val menuOptionsList = listOf(
        MenuDto("Notas", ImageVector.vectorResource(R.drawable.ic_notes)),
        MenuDto("Incidencias", ImageVector.vectorResource(R.drawable.ic_warning)),
        MenuDto("Asistencia", ImageVector.vectorResource(R.drawable.ic_calendar)),
        MenuDto("Pagos", ImageVector.vectorResource(R.drawable.ic_warning)),
    )

    val pagedList = remember(menuOptionsList) { menuOptionsList.chunked(3) }
    val pagerState = rememberPagerState(pageCount = { pagedList.size })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(bottom = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 8.dp),
//            pageSpacing = 16.dp,
//            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            // Cada página ahora es una fila con sus elementos
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                pagedList[pageIndex].forEach { option ->
                    // Usamos weight(1f) para que los 3 ocupen el mismo espacio
                    Box(modifier = Modifier.weight(1f)) {
                        MenuCard(option = option)
                    }
                }

                // Rellenar espacios vacíos si la última página tiene menos de 3
                if (pagedList[pageIndex].size < 3) {
                    repeat(3 - pagedList[pageIndex].size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Indicadores inferiores
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagedList.size) { index ->
                val isSelected = pagerState.currentPage == index
                val color =
                    if (isSelected) colorResource(R.color.secondary) else colorResource(R.color.third)

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(width = if (isSelected) 16.dp else 13.dp, height = 8.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}

@Composable
fun SectionCommunication(lista: List<CommunicationDto>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Comunicados",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.secondary)
                )
            )
            Text(
                text = "Ver todo",
                style = TextStyle(
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(R.color.text_third)
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
                CommunicationCard(item)
            }
        }
    }
}