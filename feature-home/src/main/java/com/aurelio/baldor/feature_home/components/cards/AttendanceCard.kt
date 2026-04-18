package com.aurelio.baldor.feature_home.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aurelio.baldor.core.R
import com.aurelio.baldor.feature_home.components.dialogs.DelayDialog
import com.aurelio.baldor.feature_home.components.dialogs.NonAttendanceDialog

data class AsistenciaDto(
    val estado: String,
    val fecha: String,
    val hora_inicio: String,
    val hora_fin: String
)

@Composable
fun WithoutAttendance(asistenciaDto: AsistenciaDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.card_gray))
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = colorResource(R.color.text_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = asistenciaDto.fecha,
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_primary)
                    )
                }

                Surface(
                    color = colorResource(R.color.text_fourth),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Sin Asistencia",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AttendancePresent(asistenciaDto: AsistenciaDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.success_third))
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = colorResource(R.color.text_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = asistenciaDto.fecha,
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_primary)
                    )
                }

                Surface(
                    color = colorResource(R.color.success_secondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Presente",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = colorResource(R.color.success_primary),
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ingreso:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_inicio.take(5),
                        color = colorResource(if (asistenciaDto.hora_inicio != "-") R.color.success_primary else R.color.text_third),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }

                // Columna Salida
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Salida:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_fin.take(5),
                        color = colorResource(if (asistenciaDto.hora_fin != "-") R.color.success_primary else R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceDelay(asistenciaDto: AsistenciaDto) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.warning_third))
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = colorResource(R.color.text_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = asistenciaDto.fecha,
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_primary)
                    )
                }

                Surface(
                    color = colorResource(R.color.warning_secondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Tardanza",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = colorResource(R.color.warning_primary),
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ingreso:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_inicio.take(5),
                        color = colorResource(if (asistenciaDto.hora_inicio != "-") R.color.warning_fourth else R.color.text_third),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }

                // Columna Salida
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Salida:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_fin.take(5),
                        color = colorResource(if (asistenciaDto.hora_fin != "-") R.color.warning_fourth else R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila Inferior: Link de Justificación
            if (asistenciaDto.estado == "TI") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable() {
                        showDialog = true
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_paper),
                        contentDescription = null,
                        tint = colorResource(R.color.warning_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Justificar tardanza",
                        color = colorResource(R.color.warning_primary),
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    if (showDialog) {
        DelayDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun NonAttendance(asistenciaDto: AsistenciaDto) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.error_third))
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
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = colorResource(R.color.text_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = asistenciaDto.fecha,
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_primary)
                    )
                }

                Surface(
                    color = colorResource(R.color.error_secondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Inasistencia",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = colorResource(R.color.error_primary),
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ingreso:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_inicio.take(5),
                        color = colorResource(if (asistenciaDto.hora_inicio != "-") R.color.error_primary else R.color.text_third),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }

                // Columna Salida
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                            contentDescription = null,
                            tint = colorResource(R.color.text_third),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Salida:",
                            color = colorResource(R.color.text_third),
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = asistenciaDto.hora_fin.take(5),
                        color = colorResource(if (asistenciaDto.hora_fin != "-") R.color.error_primary else R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila Inferior: Link de Justificación
            if (asistenciaDto.estado == "FI") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable() {
                        showDialog = true
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_paper),
                        contentDescription = null,
                        tint = colorResource(R.color.error_primary),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Justificar falta",
                        color = colorResource(R.color.error_primary),
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    if (showDialog) {
        NonAttendanceDialog(onDismiss = { showDialog = false })
    }
}