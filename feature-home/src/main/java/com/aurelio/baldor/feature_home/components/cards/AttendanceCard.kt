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

@Composable
fun AttendancePresent(){
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
                        text = "Lunes, 16 Febrero 2026",
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
                        Text(text = "Ingreso: 07:00AM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "07:00AM",
                        color = colorResource(R.color.success_primary),
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
                        Text(text = "Salida: 03:00PM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "-",
                        color = colorResource(R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceDelay(){
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
                        text = "Lunes, 16 Febrero 2026",
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
                        Text(text = "Ingreso: 07:00AM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "07:40AM",
                        color = colorResource(R.color.warning_fourth),
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
                        Text(text = "Salida: 03:00PM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "-",
                        color = colorResource(R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila Inferior: Link de Justificación
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(){
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

    if(showDialog){
        DelayDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun NonAttendance(){
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
                        text = "Lunes, 16 Febrero 2026",
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
                        Text(text = "Ingreso: 07:00AM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "-",
                        color = colorResource(R.color.text_third),
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
                        Text(text = "Salida: 03:00PM", color = colorResource(R.color.text_third), fontSize = 12.sp)
                    }
                    Text(
                        text = "-",
                        color = colorResource(R.color.text_third),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila Inferior: Link de Justificación
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(){
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

    if(showDialog){
        NonAttendanceDialog(onDismiss = { showDialog = false })
    }
}