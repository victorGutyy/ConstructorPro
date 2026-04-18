package com.victor.constructorpro.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.victor.constructorpro.data.local.DatabaseProvider
import com.victor.constructorpro.data.local.NotaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(
    navController: NavHostController,
    application: Application
) {
    val viewModel: NotasViewModel = viewModel(
        factory = NotasViewModelFactory(application)
    )

    val notas by viewModel.notas.collectAsState()

    var notaId by remember { mutableStateOf<Int?>(null) }
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda de Apuntes") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = {
                        titulo = it
                        mensajeError = ""
                    },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contenido,
                    onValueChange = {
                        contenido = it
                        mensajeError = ""
                    },
                    label = { Text("Contenido") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
                )

                Button(
                    onClick = {
                        if (titulo.isBlank() || contenido.isBlank()) {
                            mensajeError = "Complete título y contenido"
                        } else {
                            viewModel.guardarNota(
                                id = notaId,
                                titulo = titulo,
                                contenido = contenido
                            )
                            notaId = null
                            titulo = ""
                            contenido = ""
                            mensajeError = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (notaId == null) "Guardar nota" else "Actualizar nota")
                }

                if (mensajeError.isNotEmpty()) {
                    Text(
                        text = mensajeError,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }

                Text(
                    text = "Notas guardadas",
                    fontSize = 20.sp
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notas) { nota ->
                    NotaCard(
                        nota = nota,
                        onEditar = {
                            notaId = nota.id
                            titulo = nota.titulo
                            contenido = nota.contenido
                        },
                        onEliminar = {
                            viewModel.eliminarNota(nota)
                            if (notaId == nota.id) {
                                notaId = null
                                titulo = ""
                                contenido = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotaCard(
    nota: NotaEntity,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = nota.titulo, fontSize = 18.sp)
            Text(text = nota.contenido)
            Text(text = nota.fechaHora, fontSize = 12.sp)

            androidx.compose.foundation.layout.Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onEditar) {
                    Text("Editar")
                }
                TextButton(onClick = onEliminar) {
                    Text("Eliminar")
                }
            }
        }
    }
}

class NotasViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = DatabaseProvider.getDatabase(application).notaDao()
        return NotasViewModel(dao) as T
    }
}