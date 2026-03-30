package com.aurelio.baldor.feature_home.components.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aurelio.baldor.core.R

@Composable
fun NonAttendanceDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Justificar Falta",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.text_primary)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(R.color.text_primary)
                        )
                    }
                }

                // Fecha
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = colorResource(R.color.text_third),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Fecha: 05 Feb",
                        color = colorResource(R.color.text_primary),
                        fontSize = 16.sp
                    )
                }

                // Selector de Motivo (OutlinedTextField con trailing icon)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Motivo") },
                    placeholder = { Text("Selecciona una opción") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, null) },
                    readOnly = true
                )

                // Detalle
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Detalle") },
                    placeholder = { Text("Ingrese el detalle de la Falta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(146.dp),
                    maxLines = 5
                )

                // Adjuntar Archivo (Box con borde punteado o redondeado)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(146.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                        .clickable { /* Acción de adjuntar */ },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(id = R.drawable.ic_attach), contentDescription = null)
                        Text(
                            "Adjuntar foto o documento",
                            color = colorResource(R.color.text_fourth),
                            fontSize = 14.sp
                        )
                    }
                }

                // Botón Enviar
                Button(
                    onClick = { /* Lógica de envío */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Enviar", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}