package com.victor.constructorpro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.victor.constructorpro.R
import com.victor.constructorpro.ui.navigation.AppNavigation
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val currentDateTime = remember { mutableStateOf(getCurrentDateTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentDateTime.value = getCurrentDateTime()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFF4F6F8))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔷 LOGO
        Image(
            painter = painterResource(id = R.drawable.logo_constructor_pro),
            contentDescription = "Logo Constructor Pro",
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Fit
        )

        // 🔥 FIRMA
        Text(
            text = "Constructor Pro VG",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238)
        )

        Text(
            text = currentDateTime.value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF546E7A)
        )

        // 📌 DESCRIPCIÓN
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Herramienta profesional para cálculos de construcción",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Text(
                    text = "Diseñada para uso real en obra: rápida, práctica y precisa.",
                    fontSize = 15.sp,
                    color = Color(0xFF455A64)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ⚡ ACCESOS RÁPIDOS
        Text(
            text = "Accesos rápidos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238),
            modifier = Modifier.align(Alignment.Start)
        )

        QuickAccessButton(
            text = "Cubicación de Concreto",
            onClick = { navController.navigate(AppNavigation.ROUTE_CUBICACION) }
        )

        QuickAccessButton(
            text = "Dosificación por PSI",
            onClick = { navController.navigate(AppNavigation.ROUTE_DOSIFICACION) }
        )

        QuickAccessButton(
            text = "Calculadora de Acero",
            onClick = { navController.navigate(AppNavigation.ROUTE_CALCULADORA_ACERO) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🧱 NUEVA SECCIÓN: TIPS DE OBRA
        Text(
            text = "Tips de obra",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238),
            modifier = Modifier.align(Alignment.Start)
        )

        TipCard(
            titulo = "Escuadra 3-4-5",
            descripcion = "Para sacar escuadra: 3m x 4m → diagonal debe dar 5m."
        )

        TipCard(
            titulo = "Plomo",
            descripcion = "Herramienta para verificar la verticalidad exacta de muros y columnas."
        )

        TipCard(
            titulo = "Nivel",
            descripcion = "Burbuja: uso básico. Manguera: distancias largas. Láser: alta precisión."
        )

        TipCard(
            titulo = "Consejo",
            descripcion = "Siempre verifica nivel y escuadra antes de fundir concreto."
        )
    }
}

@Composable
fun QuickAccessButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TipCard(
    titulo: String,
    descripcion: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color(0xFF455A64)
            )
        }
    }
}

private fun getCurrentDateTime(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy  HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}