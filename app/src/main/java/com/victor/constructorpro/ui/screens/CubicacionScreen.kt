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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.victor.constructorpro.data.local.DatabaseProvider
import com.victor.constructorpro.data.local.HistorialEntity
import com.victor.constructorpro.domain.usecase.CalculateConcreteMaterialsUseCase
import com.victor.constructorpro.domain.usecase.CalculateConcreteVolumeUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CubicacionScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val viewModel: CubicacionViewModel = viewModel(
        factory = CubicacionViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsState()

    var largo by remember { mutableStateOf("") }
    var ancho by remember { mutableStateOf("") }
    var alto by remember { mutableStateOf("") }
    var selectedPsi by remember { mutableIntStateOf(2500) }
    var lastSavedDetail by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        val state = uiState
        if (state is CubicacionUiState.Success) {
            val result = state.result

            val detalle = """
                Largo: $largo m
                Ancho: $ancho m
                Alto: $alto m
                PSI: $selectedPsi
                Volumen: ${formatNumber(result.volumeM3)} m³
                Cemento: ${formatNumber(result.cementKg)} kg
                Arena: ${formatNumber(result.sandM3)} m³
                Grava: ${formatNumber(result.gravelM3)} m³
                Agua: ${formatNumber(result.waterLiters)} litros
                Sacos 50 kg: ${formatNumber(result.cementBags50Kg)}
            """.trimIndent()

            if (detalle != lastSavedDetail) {
                lastSavedDetail = detalle

                val dao = DatabaseProvider.getDatabase(context).historialDao()

                coroutineScope.launch {
                    val fecha = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale.getDefault()
                    ).format(Date())

                    dao.insert(
                        HistorialEntity(
                            tipo = "Cubicación",
                            detalle = detalle,
                            fechaHora = fecha
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cubicación de Concreto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
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
                text = "Ingrese las dimensiones en metros",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = largo,
                onValueChange = {
                    largo = it
                    viewModel.clearError()
                },
                label = { Text("Largo (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ancho,
                onValueChange = {
                    ancho = it
                    viewModel.clearError()
                },
                label = { Text("Ancho (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = alto,
                onValueChange = {
                    alto = it
                    viewModel.clearError()
                },
                label = { Text("Espesor / Alto (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Seleccione el PSI",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            PsiOptionRow(
                psi = 2000,
                selectedPsi = selectedPsi,
                onSelected = { selectedPsi = 2000 }
            )

            PsiOptionRow(
                psi = 2500,
                selectedPsi = selectedPsi,
                onSelected = { selectedPsi = 2500 }
            )

            PsiOptionRow(
                psi = 3000,
                selectedPsi = selectedPsi,
                onSelected = { selectedPsi = 3000 }
            )

            PsiOptionRow(
                psi = 3500,
                selectedPsi = selectedPsi,
                onSelected = { selectedPsi = 3500 }
            )

            Button(
                onClick = {
                    viewModel.calculateVolumeAndMaterials(
                        largo = largo,
                        ancho = ancho,
                        alto = alto,
                        psi = selectedPsi
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Volumen y Materiales")
            }

            when (uiState) {
                is CubicacionUiState.Error -> {
                    Text(
                        text = (uiState as CubicacionUiState.Error).message,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }

                is CubicacionUiState.Success -> {
                    val result = (uiState as CubicacionUiState.Success).result

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Resultado de Obra",
                                fontSize = 22.sp
                            )

                            Text(text = "Volumen: ${formatNumber(result.volumeM3)} m³")
                            Text(text = "PSI seleccionado: ${result.psi}")
                            Text(text = "Cemento: ${formatNumber(result.cementKg)} kg")
                            Text(text = "Arena: ${formatNumber(result.sandM3)} m³")
                            Text(text = "Grava: ${formatNumber(result.gravelM3)} m³")
                            Text(text = "Agua: ${formatNumber(result.waterLiters)} litros")
                            Text(
                                text = "Sacos de Cemento:",
                                fontSize = 18.sp
                            )
                            Text(text = "• Sacos de 50 kg: ${formatNumber(result.cementBags50Kg)}")
                            Text(text = "• Sacos de 42.5 kg: ${formatNumber(result.cementBags42Kg)}")
                            Text(text = "• Sacos de 25 kg: ${formatNumber(result.cementBags25Kg)}")
                        }
                    }
                }

                CubicacionUiState.Initial -> Unit
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

class CubicacionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CubicacionViewModel(
            calculateConcreteVolumeUseCase = CalculateConcreteVolumeUseCase(),
            calculateConcreteMaterialsUseCase = CalculateConcreteMaterialsUseCase()
        ) as T
    }
}