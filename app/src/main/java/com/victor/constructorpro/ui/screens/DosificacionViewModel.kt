package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import com.victor.constructorpro.domain.model.DosificacionData
import com.victor.constructorpro.domain.model.DosificacionObraData
import com.victor.constructorpro.domain.usecase.GetDosificacionByPsiUseCase
import com.victor.constructorpro.domain.usecase.GetDosificacionObraByPsiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DosificacionViewModel(
    private val getDosificacionByPsiUseCase: GetDosificacionByPsiUseCase,
    private val getDosificacionObraByPsiUseCase: GetDosificacionObraByPsiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DosificacionUiState>(DosificacionUiState.Initial)
    val uiState: StateFlow<DosificacionUiState> = _uiState

    fun loadDosificacion(psi: Int) {
        try {
            val tecnica = getDosificacionByPsiUseCase.execute(psi)
            val obra = getDosificacionObraByPsiUseCase.execute(psi)

            _uiState.value = DosificacionUiState.Success(
                tecnica = tecnica,
                obra = obra
            )
        } catch (e: Exception) {
            _uiState.value = DosificacionUiState.Error("No se pudo obtener la dosificación")
        }
    }
}

sealed class DosificacionUiState {
    data object Initial : DosificacionUiState()

    data class Success(
        val tecnica: DosificacionData,
        val obra: DosificacionObraData
    ) : DosificacionUiState()

    data class Error(val message: String) : DosificacionUiState()
}