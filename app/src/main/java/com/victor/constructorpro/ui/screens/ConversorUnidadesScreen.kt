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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversorUnidadesScreen(navController: NavHostController) {
    var inputValue by remember { mutableStateOf("") }
    var selectedConversion by remember { mutableStateOf("Metros a Pies") }
    var expanded by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val conversionOptions = listOf(
        "Metros a Pies",
        "Pies a Metros",
        "m² a ft²",
        "ft² a m²",
        "m³ a yd³",
        "yd³ a m³",
        "kg a lb",
        "lb a kg"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversor de Unidades") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ingrese el valor a convertir",
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = inputValue,
                onValueChange = {
                    inputValue = it
                    errorText = ""
                },
                label = { Text("Valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedConversion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de conversión") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    conversionOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedConversion = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val value = inputValue.toDoubleOrNull()

                    if (value == null) {
                        errorText = "Ingrese un valor válido"
                        resultText = ""
                    } else {
                        val result = when (selectedConversion) {
                            "Metros a Pies" -> value * 3.28084
                            "Pies a Metros" -> value / 3.28084
                            "m² a ft²" -> value * 10.7639
                            "ft² a m²" -> value / 10.7639
                            "m³ a yd³" -> value * 1.30795
                            "yd³ a m³" -> value / 1.30795
                            "kg a lb" -> value * 2.20462
                            "lb a kg" -> value / 2.20462
                            else -> 0.0
                        }

                        resultText = String.format(
                            Locale.US,
                            "Resultado: %.2f",
                            result
                        )
                        errorText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Convertir")
            }

            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resultado",
                            fontSize = 20.sp
                        )
                        Text(resultText)
                    }
                }
            }
        }
    }
}