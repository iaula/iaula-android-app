package com.aurelio.baldor.feature_login.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aurelio.baldor.core.R
import com.aurelio.baldor.core.components.AlertMessage.AlertMessageUi
import com.aurelio.baldor.core.components.AlertMessage.AlertMessageViewModel
import com.aurelio.baldor.core.components.AlertMessage.AlertType
import com.aurelio.baldor.core.components.Loading.LoadingView
import com.aurelio.baldor.core.data.remote.InstitutionResponse
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    alertViewModel: AlertMessageViewModel = koinViewModel()
) {
    val alertState by alertViewModel.alertState.collectAsState()

    val viewModel: LoginViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var institution by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginMethod by remember { mutableStateOf("DNI") }

    val isLoginEnabled = uiState.selectedInstitution != null &&
            uiState.usuario.isNotBlank() &&
            uiState.password.isNotBlank()

    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.result) {
        when (val result = uiState.result) {
            is LoginResult.Success -> {
                alertViewModel.showMessage("Login exitoso", AlertType.SUCCESS)
                // Navegar a Home
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is LoginResult.Error -> {
                alertViewModel.showMessage(result.message, AlertType.ERROR)
            }

            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo y Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Colegio\nA. Baldor",
                    color = Color(0xFF606060)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Select institución educativa
            InstitutionSelector(
                institutions = uiState.institutions,
                selectedInstitution = institution,
                onInstitutionSelected = {
                    institution = it.nombre
                    viewModel.onInstitutionSelected(it)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Método de ingreso (Toggle Custom)
            Text(
                text = "Método de ingreso",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(47.dp)
                    .background(colorResource(R.color.third), RoundedCornerShape(8.dp))
            ) {
                val modifierDni = if (loginMethod == "DNI")
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(colorResource(R.color.primary), RoundedCornerShape(6.dp))
                else Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { loginMethod = "DNI" }

                val modifierCel = if (loginMethod == "Celular")
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(colorResource(R.color.primary), RoundedCornerShape(6.dp))
                else Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { loginMethod = "Celular" }

                Box(modifier = modifierDni, contentAlignment = Alignment.Center) {
                    Text(
                        "DNI",
                        color = if (loginMethod == "DNI") Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(modifier = modifierCel, contentAlignment = Alignment.Center) {
                    Text(
                        "Celular",
                        color = if (loginMethod == "Celular") Color.White else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Input usuario (DNI o Celular)
            OutlinedTextField(
                value = uiState.usuario,
                onValueChange = { viewModel.onUsuarioChanged(it) },
                label = { Text(if (loginMethod == "DNI") "Documento de identidad" else "Celular") },
                placeholder = {
                    Text(
                        if (loginMethod == "DNI") "Ingrese su DNI" else "Ingrese número",
                        color = Color.LightGray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Contraseña
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Contraseña") },
                placeholder = { Text("Ingrese su contraseña", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "¿Olvidaste tu contraseña?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clickable { /* Navegación */ },
                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón Iniciar sesión (solo activo si los campos están llenos)
            Button(
                onClick = { viewModel.login() },
                enabled = isLoginEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Iniciar sesión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Card de Admisión inferior
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F1)),
                shape = RoundedCornerShape(12.dp),
                onClick = { },
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(colorResource(R.color.secondary), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Monitor, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "Proceso de admisión",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "Matrícula o postulación de alumnos",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        alertState?.let {
            AlertMessageUi(state = it)
        }

        if (uiState.result is LoginResult.Loading) {
            LoadingView()
        }
    }
}

@Composable
fun InstitutionSelector(
    institutions: List<InstitutionResponse>,
    selectedInstitution: String,
    onInstitutionSelected: (InstitutionResponse) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Campo que parece un TextField pero no permite escribir
    Box {
        OutlinedTextField(
            value = selectedInstitution,
            onValueChange = { /* vacío: no permitimos escribir */ },
            label = { Text("Institución Educativa") },
            placeholder = { Text("Seleccione una institución") },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            enabled = true,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
            },
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { showDialog = true }
        )
    }

    if (showDialog) {
        var searchQuery by remember { mutableStateOf("") }

        val filteredInstitutions = remember(searchQuery, institutions) {
            if (searchQuery.isEmpty()) {
                institutions
            } else {
                institutions.filter { it.nombre.contains(searchQuery, ignoreCase = true) }
            }
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Instituciones") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Campo de búsqueda
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        singleLine = true
                    )

                    // Contenedor con scroll para los resultados
                    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                        items(filteredInstitutions) { institution ->
                            Text(
                                text = institution.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onInstitutionSelected(institution)
                                        showDialog = false
                                    }
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}
