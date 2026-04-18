package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.constructorpro.data.local.HistorialDao
import com.victor.constructorpro.data.local.HistorialEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistorialViewModel(
    private val historialDao: HistorialDao
) : ViewModel() {

    val historial: StateFlow<List<HistorialEntity>> =
        historialDao.getAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun borrarTodo() {
        viewModelScope.launch {
            historialDao.deleteAll()
        }
    }

    fun guardar(tipo: String, detalle: String) {
        viewModelScope.launch {
            val fecha = java.text.SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

            val item = HistorialEntity(
                tipo = tipo,
                detalle = detalle,
                fechaHora = fecha
            )

            historialDao.insert(item)
        }
    }
}