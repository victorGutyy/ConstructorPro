package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.constructorpro.domain.model.ConcreteCalculation
import com.victor.constructorpro.domain.usecase.CalculateConcreteVolumeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CubicacionViewModel(
    private val calculateConcreteVolumeUseCase: CalculateConcreteVolumeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CubicacionUiState>(CubicacionUiState.Initial)
    val uiState: StateFlow<CubicacionUiState> = _uiState

    fun calculateVolume(largo: String, ancho: String, alto: String) {
        val l = largo.toDoubleOrNull()
        val a = ancho.toDoubleOrNull()
        val h = alto.toDoubleOrNull()

        if (l == null || a == null || h == null || l <= 0 || a <= 0 || h <= 0) {
            _uiState.value = CubicacionUiState.Error("Ingrese valores válidos mayores a 0")
            return
        }

        viewModelScope.launch {
            try {
                val result = calculateConcreteVolumeUseCase.execute(l, a, h)
                _uiState.value = CubicacionUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = CubicacionUiState.Error("Error en el cálculo")
            }
        }
    }

    fun clearError() {
        if (_uiState.value is CubicacionUiState.Error) {
            _uiState.value = CubicacionUiState.Initial
        }
    }
}

sealed class CubicacionUiState {
    object Initial : CubicacionUiState()
    data class Success(val result: ConcreteCalculation) : CubicacionUiState()
    data class Error(val message: String) : CubicacionUiState()
}
