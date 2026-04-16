package com.victor.constructorpro.ui.screens

import com.victor.constructorpro.domain.model.ConversionPaladasResult

sealed class ConversionPaladasUiState {
    data object Initial : ConversionPaladasUiState()
    data class Success(val result: ConversionPaladasResult) : ConversionPaladasUiState()
    data class Error(val message: String) : ConversionPaladasUiState()
}