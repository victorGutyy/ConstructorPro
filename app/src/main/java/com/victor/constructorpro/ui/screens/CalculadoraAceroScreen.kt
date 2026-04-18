package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.data.local.DatabaseProvider
import com.victor.constructorpro.data.local.HistorialEntity
import com.victor.constructorpro.domain.usecase.CalculateAceroLongitudinalUseCase
import com.victor.constructorpro.domain.usecase.CalculateFlejesUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraAceroScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val longitudinalUseCase = remember { CalculateAceroLongitudinalUseCase() }
    val flejesUseCase = remember { CalculateFlejesUseCase() }

    var selectedMode by remember { mutableStateOf("Longitudinal") }

    var longitudInput by remember { mutableStateOf("") }
    var cantidadVarillasInput by remember { mutableStateOf("") }
    var longitudComercialInput by remember { mutableStateOf("6") }
    var traslapoInput by remember { mutableStateOf("0.20") }

    var espaciamientoInput by remember { mutableStateOf("0.15") }

    var errorText by remember { mutableStateOf("") }

    var metrosLinealesTotales by remember { mutableDoubleStateOf(0.0) }
    var longitudUtilPorVarilla by remember { mutableDoubleStateOf(0.0) }
    var cantidadVarillasComerciales by remember { mutableIntStateOf(0) }
    var cantidadFlejes by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Acero") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
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
            Text(
                text = "Seleccione el tipo de cálculo",
                fontSize = 16.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMode == "Longitudinal",
                    onClick = { selectedMode = "Longitudinal" }
                )
                Text("Acero longitudinal")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMode == "Flejes",
                    onClick = { selectedMode = "Flejes" }
                )
                Text("Flejes")
            }

            OutlinedTextField(
                value = longitudInput,
                onValueChange = { longitudInput = it },
                label = { Text("Longitud total del elemento (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            if (selectedMode == "Longitudinal") {
                OutlinedTextField(
                    value = cantidadVarillasInput,
                    onValueChange = { cantidadVarillasInput = it },
                    label = { Text("Cantidad de varillas del elemento") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = longitudComercialInput,
                    onValueChange = { longitudComercialInput = it },
                    label = { Text("Longitud comercial de varilla (m)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = traslapoInput,
                    onValueChange = { traslapoInput = it },
                    label = { Text("Traslapo (m)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (selectedMode == "Flejes") {
                OutlinedTextField(
                    value = espaciamientoInput,
                    onValueChange = { espaciamientoInput = it },
                    label = { Text("Espaciamiento entre flejes (m)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    try {
                        if (longitudInput.isBlank()) {
                            throw IllegalArgumentException("Ingrese la longitud del elemento")
                        }

                        val longitud = longitudInput.toDoubleOrNull()
                            ?: throw IllegalArgumentException("Longitud inválida")

                        if (selectedMode == "Longitudinal") {
                            if (cantidadVarillasInput.isBlank()) {
                                throw IllegalArgumentException("Ingrese la cantidad de varillas")
                            }

                            val cantidadVarillas = cantidadVarillasInput.toIntOrNull()
                                ?: throw IllegalArgumentException("Ingrese una cantidad válida de varillas")

                            val longitudComercial = longitudComercialInput.toDoubleOrNull()
                                ?: throw IllegalArgumentException("Ingrese una longitud comercial válida")

                            val traslapo = traslapoInput.toDoubleOrNull()
                                ?: throw IllegalArgumentException("Ingrese un traslapo válido")

                            val result = longitudinalUseCase.execute(
                                longitudTotalElemento = longitud,
                                cantidadVarillasElemento = cantidadVarillas,
                                longitudComercial = longitudComercial,
                                traslapo = traslapo
                            )

                            metrosLinealesTotales = result.metrosLinealesTotales
                            longitudUtilPorVarilla = result.longitudUtilPorVarilla
                            cantidadVarillasComerciales = result.cantidadVarillasComerciales
                            cantidadFlejes = 0

                            val detalle = """
                                Tipo: Acero longitudinal
                                Longitud: ${format(longitud)} m
                                Varillas del elemento: $cantidadVarillas
                                Longitud comercial: ${format(longitudComercial)} m
                                Traslapo: ${format(traslapo)} m
                                Metros lineales totales: ${format(metrosLinealesTotales)} m
                                Longitud útil por varilla: ${format(longitudUtilPorVarilla)} m
                                Varillas comerciales: $cantidadVarillasComerciales
                            """.trimIndent()

                            guardarEnHistorial(
                                context = context,
                                coroutineScope = coroutineScope,
                                tipo = "Calculadora de Acero",
                                detalle = detalle
                            )
                        } else {
                            val espaciamiento = espaciamientoInput.toDoubleOrNull()
                                ?: throw IllegalArgumentException("Ingrese un espaciamiento válido")

                            val result = flejesUseCase.execute(
                                longitudTotalElemento = longitud,
                                espaciamiento = espaciamiento
                            )

                            cantidadFlejes = result.cantidadFlejes
                            metrosLinealesTotales = 0.0
                            longitudUtilPorVarilla = 0.0
                            cantidadVarillasComerciales = 0

                            val detalle = """
                                Tipo: Flejes
                                Longitud: ${format(longitud)} m
                                Espaciamiento: ${format(espaciamiento)} m
                                Cantidad de flejes: $cantidadFlejes
                            """.trimIndent()

                            guardarEnHistorial(
                                context = context,
                                coroutineScope = coroutineScope,
                                tipo = "Calculadora de Acero",
                                detalle = detalle
                            )
                        }

                        errorText = ""
                    } catch (e: Exception) {
                        errorText = e.message ?: "Error en el cálculo"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (selectedMode == "Longitudinal" && cantidadVarillasComerciales > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Resultado", fontSize = 20.sp)
                        Text("Metros lineales totales: ${format(metrosLinealesTotales)} m")
                        Text("Longitud útil por varilla: ${format(longitudUtilPorVarilla)} m")
                        Text("Varillas comerciales necesarias: $cantidadVarillasComerciales")
                    }
                }
            }

            if (selectedMode == "Flejes" && cantidadFlejes > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Resultado", fontSize = 20.sp)
                        Text("Cantidad de flejes necesarios: $cantidadFlejes")
                    }
                }
            }
        }
    }
}

private fun guardarEnHistorial(
    context: android.content.Context,
    coroutineScope: kotlinx.coroutines.CoroutineScope,
    tipo: String,
    detalle: String
) {
    val dao = DatabaseProvider.getDatabase(context).historialDao()

    coroutineScope.launch {
        val fecha = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        dao.insert(
            HistorialEntity(
                tipo = tipo,
                detalle = detalle,
                fechaHora = fecha
            )
        )
    }
}

private fun format(value: Double): String {
    return String.format(Locale.US, "%.2f", value)
}