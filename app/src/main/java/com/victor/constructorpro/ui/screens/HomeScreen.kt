package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.ui.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Constructor Pro") }
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
                text = "Selecciona una herramienta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            MenuButton(
                title = "Cubicación de Concreto",
                onClick = { navController.navigate(AppNavigation.ROUTE_CUBICACION) }
            )

            MenuButton(
                title = "Dosificación por PSI",
                onClick = { navController.navigate(AppNavigation.ROUTE_DOSIFICACION) }
            )

            MenuButton(
                title = "Conversión a Paladas",
                onClick = { navController.navigate(AppNavigation.ROUTE_CONVERSION_PALADAS) }
            )

            MenuButton(
                title = "Sacos de Cemento",
                onClick = { navController.navigate(AppNavigation.ROUTE_SACOS_CEMENTO) }
            )

            MenuButton(
                title = "Tabla de Varillas",
                onClick = { navController.navigate(AppNavigation.ROUTE_TABLA_VARILLAS) }
            )

            MenuButton(
                title = "Calculadora de Acero",
                onClick = { navController.navigate(AppNavigation.ROUTE_CALCULADORA_ACERO) }
            )

            MenuButton(
                title = "Conversor de Unidades",
                onClick = { navController.navigate(AppNavigation.ROUTE_CONVERSOR_UNIDADES) }
            )

            MenuButton(
                title = "Historial de Cálculos",
                onClick = { navController.navigate(AppNavigation.ROUTE_HISTORIAL) }
            )
        }
    }
}

@Composable
fun MenuButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.padding(12.dp)
        )
    }
}