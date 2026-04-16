package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CubicacionScreen(navController: NavHostController) {
    val viewModel: CubicacionViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var largo by remember { mutableStateOf("") }
    var ancho by remember { mutableStateOf("") }
    var alto by remember { mutableStateOf("") }

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
                label = { Text("Alto (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.calculateVolume(largo, ancho, alto)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Volumen")
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
                    Text(
                        text = "Volumen: ${result.formattedVolume} ${result.unit}",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                else -> {
                    // Estado inicial, no mostrar nada
                }
            }
        }
    }
}
