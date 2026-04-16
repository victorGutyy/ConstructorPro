package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.domain.usecase.CalculateSteelWeightUseCase
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraAceroScreen(navController: NavHostController) {

    val useCase = remember { CalculateSteelWeightUseCase() }

    var longitudInput by remember { mutableStateOf("") }
    var cantidadInput by remember { mutableStateOf("") }
    var selectedVarilla by remember { mutableStateOf("#4") }
    var resultado by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val varillas = listOf(
        "#2", "#3", "#4", "#5", "#6",
        "#7", "#8", "#9", "#10", "#12"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Acero") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Seleccione el tipo de varilla", fontSize = 16.sp)

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedVarilla,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Varilla") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    varillas.forEach { varilla ->
                        DropdownMenuItem(
                            text = { Text(varilla) },
                            onClick = {
                                selectedVarilla = varilla
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = longitudInput,
                onValueChange = { longitudInput = it },
                label = { Text("Longitud (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cantidadInput,
                onValueChange = { cantidadInput = it },
                label = { Text("Cantidad de barras") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val longitud = longitudInput.toDoubleOrNull()
                    val cantidad = cantidadInput.toIntOrNull()

                    if (longitud == null || cantidad == null || longitud <= 0 || cantidad <= 0) {
                        error = "Ingrese valores válidos"
                        resultado = null
                    } else {
                        val result = useCase.execute(
                            selectedVarilla,
                            longitud,
                            cantidad
                        )
                        resultado = String.format(
                            Locale.US,
                            "Peso total: %.2f kg",
                            result.pesoTotal
                        )
                        error = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            resultado?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Resultado", fontSize = 18.sp)
                        Text(it)
                    }
                }
            }
        }
    }
}