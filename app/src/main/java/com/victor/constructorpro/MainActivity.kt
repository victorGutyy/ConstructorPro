package com.victor.constructorpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.victor.constructorpro.ui.components.AppDrawer
import com.victor.constructorpro.ui.navigation.AppNavigation
import com.victor.constructorpro.ui.screens.CubicacionScreen
import com.victor.constructorpro.ui.screens.HomeScreen
import com.victor.constructorpro.ui.screens.PlaceholderScreen
import com.victor.constructorpro.ui.screens.DosificacionScreen
import com.victor.constructorpro.ui.theme.ConstructorProTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConstructorProTheme {
                AppContainer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContainer() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppNavigation.ROUTE_HOME

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                AppDrawer(
                    currentRoute = currentRoute,
                    onItemSelected = { route ->
                        coroutineScope.launch {
                            drawerState.close()
                        }

                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Constructor Pro",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF0D47A1)
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppNavigation.ROUTE_HOME
            ) {
                composable(AppNavigation.ROUTE_HOME) {
                    HomeScreen(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }

                composable(AppNavigation.ROUTE_CUBICACION) {
                    CubicacionScreen(navController = navController)
                }

                composable(AppNavigation.ROUTE_DOSIFICACION) {
                    DosificacionScreen(navController = navController)
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
    }
}