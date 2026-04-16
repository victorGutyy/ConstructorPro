package com.victor.constructorpro.ui.screens

import androidx.lifecycle.ViewModel
import com.victor.constructorpro.domain.usecase.CalculatePaladasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConversionPaladasViewModel(
    private val calculatePaladasUseCase: CalculatePaladasUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ConversionPaladasUiState>(ConversionPaladasUiState.Initial)
    val uiState: StateFlow<ConversionPaladasUiState> = _uiState.asStateFlow()

    fun calculate(
        bagsInput: String,
        psi: Int
    ) {
        val cementBags = bagsInput.toDoubleOrNull()

        if (cementBags == null || cementBags <= 0) {
            _uiState.value = ConversionPaladasUiState.Error(
                "Ingrese una cantidad válida de sacos de cemento"
            )
            return
        }

        try {
            val result = calculatePaladasUseCase.execute(
                psi = psi,
                cementBags = cementBags
            )
            _uiState.value = ConversionPaladasUiState.Success(result)
        } catch (e: Exception) {
            _uiState.value = ConversionPaladasUiState.Error(
                e.message ?: "Ocurrió un error al calcular"
            )
        }
    }

    fun clearError() {
        if (_uiState.value is ConversionPaladasUiState.Error) {
            _uiState.value = ConversionPaladasUiState.Initial
        }
    }
}