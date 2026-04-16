package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.domain.usecase.GetDosificacionObraByPsiUseCase
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionPaladasScreen(navController: NavHostController) {
    var cementBagsInput by remember { mutableStateOf("") }
    var selectedPsi by remember { mutableIntStateOf(2500) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var cementoPaladas by remember { mutableDoubleStateOf(0.0) }
    var arenaPaladas by remember { mutableDoubleStateOf(0.0) }
    var gravaPaladas by remember { mutableDoubleStateOf(0.0) }
    var aguaBaldes by remember { mutableDoubleStateOf(0.0) }

    val obraUseCase = remember { GetDosificacionObraByPsiUseCase() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversión a Paladas") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingrese la cantidad de bultos de cemento de 50 kg",
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = cementBagsInput,
                onValueChange = {
                    cementBagsInput = it
                    errorMessage = null
                },
                label = { Text("Cantidad de bultos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Seleccione el PSI",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            PsiOptionRow(2000, selectedPsi) { selectedPsi = 2000 }
            PsiOptionRow(2500, selectedPsi) { selectedPsi = 2500 }
            PsiOptionRow(3000, selectedPsi) { selectedPsi = 3000 }
            PsiOptionRow(3500, selectedPsi) { selectedPsi = 3500 }

            Button(
                onClick = {
                    val bultos = cementBagsInput.toDoubleOrNull()

                    if (bultos == null || bultos <= 0) {
                        errorMessage = "Ingrese una cantidad válida de bultos"
                    } else {
                        val base = obraUseCase.execute(selectedPsi)

                        cementoPaladas = base.cementoPaladas * bultos
                        arenaPaladas = base.arenaPaladas * bultos
                        gravaPaladas = base.gravaPaladas * bultos
                        aguaBaldes = base.aguaBaldes * bultos
                        errorMessage = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red
                )
            }

            if (cementoPaladas > 0.0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resultado práctico de obra",
                            fontSize = 22.sp
                        )

                        Text("PSI seleccionado: $selectedPsi")
                        Text("Cemento: ${formatNumber(cementoPaladas)} paladas")
                        Text("Arena: ${formatNumber(arenaPaladas)} paladas")
                        Text("Grava: ${formatNumber(gravaPaladas)} paladas")
                        Text("Agua: ${formatNumber(aguaBaldes)} baldes")
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedPsi == psi,
            onClick = onSelected
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$psi PSI")
    }
}

private fun formatNumber(value: Double): String {
    return String.format(Locale.US, "%.2f", value)
}