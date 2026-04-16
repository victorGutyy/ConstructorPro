package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.victor.constructorpro.domain.model.VarillaData
import com.victor.constructorpro.domain.usecase.GetTablaVarillasUseCase
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablaVarillasScreen(navController: NavHostController) {
    val viewModel: TablaVarillasViewModel = viewModel(
        factory = TablaVarillasViewModelFactory()
    )
    val tabla by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tabla de Varillas") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Tabla referencial de diámetros, peso por metro y peso por barra de 12 m",
                    fontSize = 16.sp
                )
            }

            items(tabla) { varilla ->
                VarillaCard(varilla = varilla)
            }
        }
    }
}

@Composable
private fun VarillaCard(varilla: VarillaData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Varilla ${varilla.numero}",
                fontSize = 20.sp
            )
            Text("Medida nominal: ${varilla.medidaPulgadas}")
            Text("Diámetro: ${format(varilla.diametroMm)} mm")
            Text("Peso por metro: ${format(varilla.pesoKgM)} kg/m")
            Text("Longitud comercial: ${format(varilla.longitudM)} m")
            Text("Peso por barra: ${format(varilla.pesoPorBarraKg)} kg")
        }
    }
}

private fun format(value: Double): String {
    return String.format(Locale.US, "%.2f", value)
}

class TablaVarillasViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TablaVarillasViewModel(
            getTablaVarillasUseCase = GetTablaVarillasUseCase()
        ) as T
    }
}