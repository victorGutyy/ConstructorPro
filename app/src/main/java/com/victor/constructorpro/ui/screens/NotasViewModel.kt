package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.constructorpro.data.local.NotaDao
import com.victor.constructorpro.data.local.NotaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotasViewModel(
    private val notaDao: NotaDao
) : ViewModel() {

    val notas: StateFlow<List<NotaEntity>> =
        notaDao.getAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun guardarNota(id: Int?, titulo: String, contenido: String) {
        if (titulo.isBlank() || contenido.isBlank()) return

        viewModelScope.launch {
            val fecha = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
            ).format(Date())

            val nota = NotaEntity(
                id = id ?: 0,
                titulo = titulo,
                contenido = contenido,
                fechaHora = fecha
            )

            if (id == null) {
                notaDao.insert(nota)
            } else {
                notaDao.update(nota)
            }
        }
    }

    fun eliminarNota(nota: NotaEntity) {
        viewModelScope.launch {
            notaDao.delete(nota)
        }
    }
}