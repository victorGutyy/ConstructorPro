package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.victor.constructorpro.domain.usecase.GetDosificacionByPsiUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosificacionScreen(navController: NavHostController) {
    val viewModel: DosificacionViewModel = viewModel(
        factory = DosificacionViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dosificación por PSI") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Seleccione la resistencia del concreto",
                fontSize = 18.sp
            )

            PsiButton(2000) { viewModel.loadDosificacion(2000) }
            PsiButton(2500) { viewModel.loadDosificacion(2500) }
            PsiButton(3000) { viewModel.loadDosificacion(3000) }
            PsiButton(3500) { viewModel.loadDosificacion(3500) }

            when (uiState) {
                is DosificacionUiState.Success -> {
                    val data = (uiState as DosificacionUiState.Success).data

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Dosificación para ${data.psi} PSI", fontSize = 20.sp)
                            Text("Cemento: ${data.cementoKg} kg")
                            Text("Arena: ${data.arenaM3} m³")
                            Text("Grava: ${data.gravaM3} m³")
                            Text("Agua: ${data.aguaLitros} litros")
                        }
                    }
                }

                is DosificacionUiState.Error -> {
                    Text(
                        text = (uiState as DosificacionUiState.Error).message,
                        color = Color.Red
                    )
                }

                DosificacionUiState.Initial -> {
                }
            }
        }
    }
}

@Composable
private fun PsiButton(
    psi: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "$psi PSI")
    }
}

class DosificacionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DosificacionViewModel(
            getDosificacionByPsiUseCase = GetDosificacionByPsiUseCase()
        ) as T
    }
}