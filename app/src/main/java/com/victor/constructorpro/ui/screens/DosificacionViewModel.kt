package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import com.victor.constructorpro.domain.model.DosificacionData
import com.victor.constructorpro.domain.usecase.GetDosificacionByPsiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DosificacionViewModel(
    private val getDosificacionByPsiUseCase: GetDosificacionByPsiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DosificacionUiState>(DosificacionUiState.Initial)
    val uiState: StateFlow<DosificacionUiState> = _uiState

    fun loadDosificacion(psi: Int) {
        try {
            val result = getDosificacionByPsiUseCase.execute(psi)
            _uiState.value = DosificacionUiState.Success(result)
        } catch (e: Exception) {
            _uiState.value = DosificacionUiState.Error("No se pudo obtener la dosificación")
        }
    }
}

sealed class DosificacionUiState {
    object Initial : DosificacionUiState()
    data class Success(val data: DosificacionData) : DosificacionUiState()
    data class Error(val message: String) : DosificacionUiState()
}