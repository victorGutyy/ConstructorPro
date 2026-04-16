package com.victor.constructorpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victor.constructorpro.ui.navigation.AppNavigation
import com.victor.constructorpro.ui.screens.CubicacionScreen
import com.victor.constructorpro.ui.screens.HomeScreen
import com.victor.constructorpro.ui.screens.PlaceholderScreen
import com.victor.constructorpro.ui.theme.ConstructorProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConstructorProTheme {
                NavigationWrapper()
            }
        }
    }
}

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavigation.ROUTE_HOME
    ) {
        composable(AppNavigation.ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(AppNavigation.ROUTE_CUBICACION) {
            CubicacionScreen(navController)
        }
        composable(AppNavigation.ROUTE_DOSIFICACION) {
            PlaceholderScreen(
                title = "Dosificación por PSI",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_CONVERSION_PALADAS) {
            PlaceholderScreen(
                title = "Conversión a Paladas",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_SACOS_CEMENTO) {
            PlaceholderScreen(
                title = "Sacos de Cemento",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_TABLA_VARILLAS) {
            PlaceholderScreen(
                title = "Tabla de Varillas",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_CALCULADORA_ACERO) {
            PlaceholderScreen(
                title = "Calculadora de Acero",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_CONVERSOR_UNIDADES) {
            PlaceholderScreen(
                title = "Conversor de Unidades",
                navController = navController
            )
        }
        composable(AppNavigation.ROUTE_HISTORIAL) {
            PlaceholderScreen(
                title = "Historial de Cálculos",
                navController = navController
            )
        }
    }
}