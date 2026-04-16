package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.domain.usecase.CalculateCementBagsUseCase
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SacosCementoScreen(navController: NavHostController) {

    val useCase = remember { CalculateCementBagsUseCase() }

    var volumeInput by remember { mutableStateOf("") }
    var selectedPsi by remember { mutableIntStateOf(2500) }
    var result by remember { mutableStateOf<com.victor.constructorpro.domain.usecase.CementBagsResult?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sacos de Cemento") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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

            Text("Ingrese el volumen de concreto en m³", fontSize = 16.sp)

            OutlinedTextField(
                value = volumeInput,
                onValueChange = {
                    volumeInput = it
                    errorMessage = null
                },
                label = { Text("Volumen (m³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Seleccione el PSI", fontSize = 16.sp)

            PsiOptionRow(2000, selectedPsi) { selectedPsi = 2000 }
            PsiOptionRow(2500, selectedPsi) { selectedPsi = 2500 }
            PsiOptionRow(3000, selectedPsi) { selectedPsi = 3000 }
            PsiOptionRow(3500, selectedPsi) { selectedPsi = 3500 }

            Button(
                onClick = {
                    val volume = volumeInput.toDoubleOrNull()
                    if (volume == null || volume <= 0) {
                        errorMessage = "Ingrese un volumen válido"
                    } else {
                        result = useCase.execute(volume, selectedPsi)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            result?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Resultado", fontSize = 20.sp)
                        Text("Volumen: ${format(it.volumeM3)} m³")
                        Text("PSI seleccionado: ${it.psi}")
                        Text("Cemento requerido: ${format(it.cementKg)} kg")
                        Text("Sacos de 50 kg: ${format(it.bags50Kg)}")
                        Text("Sacos de 42.5 kg: ${format(it.bags42_5Kg)}")
                        Text("Sacos de 25 kg: ${format(it.bags25Kg)}")
                    }
                }
            }
        }
    }
}

@Composable
private fun PsiOptionRow(
    psi: Int,
    selectedPsi: Int,
    onSelected: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedPsi == psi,
            onClick = onSelected
        )
        Text(text = "$psi PSI")
    }
}

private fun format(value: Double): String {
    return String.format(Locale.US, "%.2f", value)
}