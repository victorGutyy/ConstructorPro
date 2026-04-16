package com.victor.constructorpro.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
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
import com.victor.constructorpro.R
import com.victor.constructorpro.ui.navigation.AppNavigation
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppDrawer(
    currentRoute: String,
    onItemSelected: (String) -> Unit
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
            .fillMaxWidth()
            .background(Color(0xFFECEFF1))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_constructor_pro),
                contentDescription = "Logo Constructor Pro",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Constructor Pro",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = currentDateTime.value,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF455A64)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        DrawerItem("Inicio", AppNavigation.ROUTE_HOME, currentRoute, onItemSelected)
        DrawerItem("Cubicación de Concreto", AppNavigation.ROUTE_CUBICACION, currentRoute, onItemSelected)
        DrawerItem("Dosificación por PSI", AppNavigation.ROUTE_DOSIFICACION, currentRoute, onItemSelected)
        DrawerItem("Conversión a Paladas", AppNavigation.ROUTE_CONVERSION_PALADAS, currentRoute, onItemSelected)
        DrawerItem("Sacos de Cemento", AppNavigation.ROUTE_SACOS_CEMENTO, currentRoute, onItemSelected)
        DrawerItem("Tabla de Varillas", AppNavigation.ROUTE_TABLA_VARILLAS, currentRoute, onItemSelected)
        DrawerItem("Calculadora de Acero", AppNavigation.ROUTE_CALCULADORA_ACERO, currentRoute, onItemSelected)
        DrawerItem("Conversor de Unidades", AppNavigation.ROUTE_CONVERSOR_UNIDADES, currentRoute, onItemSelected)
        DrawerItem("Historial de Cálculos", AppNavigation.ROUTE_HISTORIAL, currentRoute, onItemSelected)
    }
}

@Composable
private fun DrawerItem(
    title: String,
    route: String,
    currentRoute: String,
    onItemSelected: (String) -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                color = Color(0xFF263238)
            )
        },
        selected = currentRoute == route,
        onClick = { onItemSelected(route) }
    )
}

private fun getCurrentDateTime(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy  HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}